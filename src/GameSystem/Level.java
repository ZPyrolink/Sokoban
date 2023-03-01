package GameSystem;

import lombok.Getter;

import java.awt.*;

/** Represent a game level */
public class Level
{
    /** Name of the level */
    @Getter
    private final String name;
    /** Current cases of the level */
    private final CaseContent[][] cases;

    /**
     * Create a new level
     * @param lines Number of lines of the level
     * @param columns Number of columns of the level
     * @param name Name of the level
     */
    public Level(int lines, int columns, String name)
    {
        cases = new CaseContent[lines][columns];
        this.name = name;
    }

    /** Number of lines of the level */
    public int getLines()
    {
        return cases.length;
    }

    /** Number of columns of the level */
    public int getColumns()
    {
        return cases[0].length;
    }

    /**
     * Set one case of the level
     * @param l Line of the case
     * @param c Column of the case
     * @param value Value of the case
     */
    public void setCase(int l, int c, CaseContent value)
    {
        cases[l][c] = value;
    }

    /**
     * Set one case of the level
     * @param p Coordinates of the case. Warning: x is columns and y lines
     *          ({@link #cases}{@code [line][collumn]} &hArr {@link #cases}{@code [p.y][p.x]})
     * @param value Coordinates of the case
     */
    public void setCase(Point p, CaseContent value)
    {
        setCase(p.y, p.x, value);
    }

    /**
     * clear one case of the level (replace it with grounds)
     * @param line Line of the case
     * @param column Column of the case
     */
    public void clearCase(int line, int column)
    {
        cases[line][column] = null;
    }

    /**
     * Clear one case of the level (replace it with grounds)
     * @param p Coordinates of the case. Warning: x is columns and y lines
     *          ({@link #cases}{@code [line][collumn]} &hArr {@link #cases}{@code [p.y][p.x]})
     */
    public void clearCase(Point p)
    {
        clearCase(p.y, p.x);
    }

    /**
     * Get the content of the case
     * @param l Line of the case
     * @param c Column of the case
     * @return The value of {@link #cases}{@code [l][c]}
     */
    public CaseContent getCase(int l, int c)
    {
        return cases[l][c];
    }

    /**
     * Get the content of the case
     * @param p Coordinates of the case. Warning: x is columns and y lines (cases[line][collumn] ⇔ cases[p.y][p.x])
     * @return The value of {@link #cases}{@code [p.y][p.x]}
     */
    public CaseContent getCase(Point p)
    {
        return getCase(p.y, p.x);
    }

    /**
     * Get the {@link CaseContent#Player} position. Warning: x is columns and y lines
     * @return The coordinates of the Player if found, otherwise {@code null}
     */
    public Point getPlayerCo()
    {
        for (int l = 0; l < cases.length; l++)
            for (int c = 0; c < cases[l].length; c++)
                if (CaseContent.havePlayer(cases[l][c]))
                    return new Point(c, l);

        return null;
    }

    /** Indicate if the level is finished (all {@link CaseContent#Box} on {@link CaseContent#Goal}) */
    public boolean isFinished()
    {
        for (CaseContent[] aCase : cases)
            for (CaseContent cc : aCase)
                if (CaseContent.haveGoal(cc) && !CaseContent.haveBox(cc))
                    return false;

        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(getLines() * getColumns());

        for (CaseContent[] line : cases)
        {
            for (CaseContent ccase : line)
                sb.append(ccase == null ? ' ' : ccase.Value);

            sb.append("\n");
        }

        sb.append("; ").append(name).append("\n");
        return sb.toString();
    }
}

