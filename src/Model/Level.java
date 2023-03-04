package Model;

import Abstract.AbstractModel;
import Managers.Settings;
import Utils.Direction;
import Utils.NumericUtils;
import lombok.Getter;

import java.awt.*;

/**
 * Represent a game level
 */
public class Level extends AbstractModel
{
    //#region Variables

    /**
     * Name of the level
     */
    @Getter
    private final String name;
    private final CaseContent[][] cases;
    /**
     * Current cases of the level
     */
    private final CaseContent[][] currentCases;

    //#endregion

    /**
     * Create a new level
     *
     * @param lines   Number of lines of the level
     * @param columns Number of columns of the level
     * @param name    Name of the level
     */
    public Level(int lines, int columns, String name)
    {
        cases = new CaseContent[lines][columns];
        currentCases = new CaseContent[lines][columns];
        this.name = name;
    }

    //#region Indexers

    /**
     * Number of lines of the level
     */
    public int getLines()
    {
        return currentCases.length;
    }

    /**
     * Number of columns of the level
     */
    public int getColumns()
    {
        return currentCases[0].length;
    }

    /**
     * Set one case of the level
     *
     * @param l     Line of the case
     * @param c     Column of the case
     * @param value Value of the case
     */
    public void setCase(int l, int c, CaseContent value)
    {
        currentCases[l][c] = value;
    }

    /**
     * Set one case of the level
     *
     * @param p     Coordinates of the case. Warning: x is columns and y lines
     *              ({@link #currentCases}{@code [line][collumn]} &hArr {@link #currentCases}{@code [p.y][p.x]})
     * @param value Coordinates of the case
     */
    public void setCase(Point p, CaseContent value)
    {
        setCase(p.y, p.x, value);
    }

    /**
     * clear one case of the level (replace it with grounds)
     *
     * @param line   Line of the case
     * @param column Column of the case
     */
    public void clearCase(int line, int column)
    {
        currentCases[line][column] = null;
    }

    /**
     * Clear one case of the level (replace it with grounds)
     *
     * @param p Coordinates of the case. Warning: x is columns and y lines
     *          ({@link #currentCases}{@code [line][collumn]} &hArr {@link #currentCases}{@code [p.y][p.x]})
     */
    public void clearCase(Point p)
    {
        clearCase(p.y, p.x);
    }

    /**
     * Get the content of the case
     *
     * @param l Line of the case
     * @param c Column of the case
     * @return The value of {@link #currentCases}{@code [l][c]}
     */
    public CaseContent getCase(int l, int c)
    {
        return currentCases[l][c];
    }

    /**
     * Get the content of the case
     *
     * @param p Coordinates of the case. Warning: x is columns and y lines (cases[line][collumn] ⇔ cases[p.y][p.x])
     * @return The value of {@link #currentCases}{@code [p.y][p.x]}
     */
    public CaseContent getCase(Point p)
    {
        return getCase(p.y, p.x);
    }

    //#endregion

    public Dimension getLevelSize(int caseSize)
    {
        return new Dimension(getColumns() * caseSize, getLines() * caseSize + 44);
    }

    //#region Save

    /**
     * Save the cases of the level
     */
    public void save()
    {
        NumericUtils.copy(currentCases, cases);
    }

    /**
     * Reset the level to the saved cases
     */
    public void reset()
    {
        NumericUtils.copy(cases, currentCases);
    }

    //#endregion

    /**
     * Indicate if the level is finished (all {@link CaseContent#Box} on {@link CaseContent#Goal})
     */
    public boolean isFinished()
    {
        for (CaseContent[] aCase : currentCases)
            for (CaseContent cc : aCase)
                if (CaseContent.haveGoal(cc) && !CaseContent.haveBox(cc))
                    return false;

        return true;
    }

    /**
     * Get the {@link CaseContent#Player} position. Warning: x is columns and y lines
     *
     * @return The coordinates of the Player if found, otherwise {@code null}
     */
    public Point getPlayerCo()
    {
        for (int l = 0; l < currentCases.length; l++)
            for (int c = 0; c < currentCases[l].length; c++)
                if (CaseContent.havePlayer(currentCases[l][c]))
                    return new Point(c, l);

        return null;
    }

    /**
     * Check if the {@link CaseContent#Player} can move on another case
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canMove(Point nextCase)
    {
        if (nextCase.x < 0 || nextCase.x >= getColumns() ||
                nextCase.y < 0 || nextCase.y >= getLines())
            return false;

        if (Settings.debugMode)
            return true;

        Point player = getPlayerCo();

        if (player.equals(nextCase))
            return false;

        if (!NumericUtils.singleAxisMoreLess(player, nextCase, 1))
            return false;

        CaseContent cc = getCase(nextCase);

        if (cc == null)
            return true;

        switch (cc)
        {
            case Goal:
                return true;
            case Box:
            case BoxOnGoal:
                Direction d = Direction.of(player, nextCase);
                Point box = nextCase.getLocation();
                //noinspection ConstantConditions
                box.translate(d.value.x, d.value.y);

                cc = getCase(box);

                return cc == null || cc == CaseContent.Goal;

            default:
                return false;
        }
    }

    /**
     * Move the {@link CaseContent#Player} on the another case
     */
    public void move(Point nextCase)
    {
        Point player = getPlayerCo();

        if (CaseContent.haveBox(getCase(nextCase)))
        {
            Direction d = Direction.of(player, nextCase);
            if (d == null)
                throw new RuntimeException();

            Point nextBox = nextCase.getLocation();
            nextBox.translate(d.value.x, d.value.y);

            if (Settings.debugMode)
            {
                Point tmp = nextBox.getLocation();

                //noinspection StatementWithEmptyBody
                for (; CaseContent.haveBox(getCase(tmp)); tmp.translate(d.value.x, d.value.y)) ;

                for (; !tmp.equals(nextBox); tmp.translate(-d.value.x, -d.value.y))
                {
                    setCase(tmp, CaseContent.haveGoal(getCase(tmp)) ?
                            CaseContent.BoxOnGoal : CaseContent.Box);
                }
            }

            setCase(nextBox, CaseContent.haveGoal(getCase(nextBox)) ?
                    CaseContent.BoxOnGoal : CaseContent.Box);

        }

        switch (getCase(player))
        {
            case Player -> clearCase(player);
            case PlayerOnGoal -> setCase(player, CaseContent.Goal);
        }

        CaseContent cc = getCase(nextCase);
        CaseContent next = CaseContent.Player;

        if (CaseContent.haveGoal(cc))
            next = CaseContent.PlayerOnGoal;

        setCase(nextCase, next);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(getLines() * getColumns());

        for (CaseContent[] line : currentCases)
        {
            for (CaseContent ccase : line)
                sb.append(ccase == null ? ' ' : ccase.Value);

            sb.append("\n");
        }

        sb.append("; ").append(name).append("\n");
        return sb.toString();
    }
}

