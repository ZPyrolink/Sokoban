package GameSystem;

import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Game implements Iterable<Level>, Iterator<Level>, Closeable
{
    @Getter
    @Setter
    private static Game game;

    private final Scanner _scanner;
    private Level _currentLevel;

    public Game(InputStream stream)
    {
        _currentLevel = null;
        _scanner = new Scanner(stream);
    }

    public Level currentLevel()
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

        for (int l = 0; l < lines.size(); l++)
        {
            line = lines.get(l);

            for (int c = 0; c < line.length(); c++)
            {
                CaseContent cc = CaseContent.FromValue(line.charAt(c));
                if (cc == null)
                    _currentLevel.clearCase(l, c);
                else
                    _currentLevel.setCase(l, c, cc);
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
