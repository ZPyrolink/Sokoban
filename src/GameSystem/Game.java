package GameSystem;

import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/** Represent a Sokoban game */
public class Game implements Iterable<Level>, Iterator<Level>, Closeable
{
    /** Static instance of a game */
    @Getter
    @Setter
    private static Game game;

    /** Scanner reading a file game */
    private final Scanner _scanner;
    /** Current level */
    @Getter
    private Level currentLevel;

    /**
     * Create a new game
     * @param stream Stream where the levels are readed
     */
    public Game(InputStream stream)
    {
        currentLevel = null;
        _scanner = new Scanner(stream);
    }

    //region Iterator / Iterable

    @Override
    public Iterator<Level> iterator()
    {
        return this;
    }

    @Override
    public boolean hasNext()
    {
        return _scanner.hasNext();
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

        currentLevel = new Level(linesNb, columnsNb, levelName);

        for (int l = 0; l < lines.size(); l++)
        {
            line = lines.get(l);

            for (int c = 0; c < line.length(); c++)
            {
                CaseContent cc = CaseContent.of(line.charAt(c));
                if (cc == null)
                    currentLevel.clearCase(l, c);
                else
                    currentLevel.setCase(l, c, cc);
            }
        }

        return currentLevel;
    }

    @Override
    public void close()
    {
        _scanner.close();
    }

    //endregion
}
