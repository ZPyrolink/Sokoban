package GameSystem;

import lombok.Getter;

import java.awt.*;

public class Level
{
    @Getter
    private final String name;
    private final CaseContent[][] _cases;

    public Level(int lines, int columns, String name)
    {
        _cases = new CaseContent[lines][columns];
        this.name = name;
    }

    /**
     * Nombre de lignes
     */
    public int getLines()
    {
        return _cases.length;
    }

    /**
     * Nombre de colonnes
     */
    public int getColumns()
    {
        return _cases[0].length;
    }

    public void setCase(int l, int c, CaseContent value)
    {
        _cases[l][c] = value;
    }

    public void setCase(Point p, CaseContent value)
    {
        setCase(p.y, p.x, value);
    }

    /**
     * Supprime le contenu de la case à la ligne line et à la colonne column
     */
    public void clearCase(int line, int column)
    {
        _cases[line][column] = null;
    }

    public void clearCase(Point p)
    {
        clearCase(p.y, p.x);
    }

    public CaseContent getCase(int l, int c)
    {
        return _cases[l][c];
    }

    public CaseContent getCase(Point p)
    {
        return getCase(p.y, p.x);
    }

    public Point playerPosition()
    {
        for (int l = 0; l < _cases.length; l++)
            for (int c = 0; c < _cases[l].length; c++)
                if (CaseContent.isPlayer(_cases[l][c]))
                    return new Point(c, l);

        return null;
    }

    public boolean finished()
    {
        for (CaseContent[] aCase : _cases)
            for (CaseContent caseContent : aCase)
                if (caseContent == CaseContent.Goal)
                    return false;

        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(getLines() * getColumns());

        for (CaseContent[] line : _cases)
        {
            for (CaseContent ccase : line)
                sb.append(ccase == null ? ' ' : ccase.Value);

            sb.append("\n");
        }

        sb.append("; ").append(name).append("\n");
        return sb.toString();
    }
}

