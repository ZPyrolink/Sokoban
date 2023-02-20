package GameSystem;

import java.io.Closeable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Game implements Iterable<Level>, Iterator<Level>, Closeable
{
    private final Scanner _scanner;
    private Level _currentLevel;

    public Game(InputStream stream)
    {
        _currentLevel = null;
        _scanner = new Scanner(stream);
    }

    public Level Level()
    {
        return _currentLevel;
    }

    @Override
    public Iterator<Level> iterator()
    {
        return this;
    }

    @Override
    public boolean hasNext()
    {
        return !_scanner.hasNext();
    }

    @Override
    public Level next()
    {
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

        _currentLevel = new Level(linesNb, columnsNb, levelName);

        for (int i = 0; i < lines.size(); i++)
        {
            line = lines.get(i);

            for (int j = 0; j < line.length(); j++)
            {
                CaseContent cc = CaseContent.FromValue(line.charAt(j));
                if (cc == null)
                    _currentLevel.clearCase(i, j);
                else
                    switch (cc)
                    {
                        case Wall -> _currentLevel.setWallAt(i, j);
                        case Goal -> _currentLevel.setGoalAt(i, j);
                        case Player -> _currentLevel.setPlayerAt(i, j);
                        case Box -> _currentLevel.setBoxAt(i, j);
                        case PlayerOnGoal -> _currentLevel.setPlayerOnGoal(i, j);
                    }
            }
        }

        return _currentLevel;
    }

    @Override
    public void close()
    {
        _scanner.close();
    }
}
