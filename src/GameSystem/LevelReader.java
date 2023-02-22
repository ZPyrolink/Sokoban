package GameSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @deprecated Use the {@link Game} class instead
 */
@Deprecated
public class LevelReader implements Closeable
{
    private final Scanner _scanner;

    public LevelReader(InputStream stream)
    {
        _scanner = new Scanner(stream);
    }

    public Level ReadNextLevel()
    {
        if (!_scanner.hasNext())
            return null;

        ArrayList<String> lines = new ArrayList<>();
        String line;
        do
        {
            line = _scanner.nextLine();
            if (!line.equals(""))
                lines.add(line);
        } while (!line.equals("") && _scanner.hasNext());

        String levelName = null;
        ArrayList<String> comments = new ArrayList<>(lines.size());
        int linesNb = 0, columnsNb = 0;
        for (int i = lines.size() - 1; i >= 0; i--)
        {
            line = lines.get(i);
            if (!line.startsWith(";"))
            {
                linesNb++;
                if (line.length() > columnsNb)
                    columnsNb = line.length();
                continue;
            }

            comments.add(line);
            if (levelName == null)
                levelName = line.substring(line.indexOf(';') + 1).trim();
        }

        for (String comment : comments)
        {
            lines.remove(comment);
        }

        Level level = new Level(linesNb, columnsNb, levelName);

        for (int i = 0; i < lines.size(); i++)
        {
            line = lines.get(i);

            for (int j = 0; j < line.length(); j++)
            {
                CaseContent cc = CaseContent.FromValue(line.charAt(j));
                if (cc == null)
                    level.clearCase(i, j);
                else
                    switch (cc)
                    {
                        case Wall -> level.setWallAt(i, j);
                        case Goal -> level.setGoalAt(i, j);
                        case Player -> level.setPlayerAt(i, j);
                        case Box -> level.setBoxAt(i, j);
                        case PlayerOnGoal -> level.setPlayerOnGoal(i, j);
                    }
            }
        }

        return level;
    }

    @Override
    public void close()
    {
        _scanner.close();
    }
}