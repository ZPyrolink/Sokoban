package GameSystem;

import java.util.Arrays;

public class Level
{
    private String _name;
    private final CaseContent[][] _cases;

    public Level(int lines, int columns, String name)
    {
        _cases = new CaseContent[lines][columns];
        _name = name;
    }

    /**
     * permet de fixer le nom du niveau
     */
    public void fixeNom(String s)
    {
        _name = s;
    }

    /**
     * supprime le contenu de la case à la ligne line et à la colonne column
     */
    public void clearCase(int line, int column)
    {
        _cases[line][column] = null;
    }

    /**
     * Ajoute un mur à la position line, column
     */
    public void setWallAt(int line, int column)
    {
        _cases[line][column] = CaseContent.Wall;
    }

    /**
     * Ajoute un pousseur à la position line, column
     */
    public void setPlayerAt(int line, int column)
    {
        _cases[line][column] = CaseContent.Player;
    }

    /**
     * Aajoute une caisse à la position line, column
     */
    public void setBoxAt(int line, int column)
    {
        _cases[line][column] = CaseContent.Box;
    }

    /**
     * Ajoute un but à la position line, column
     */
    public void setGoalAt(int line, int column)
    {
        _cases[line][column] = CaseContent.Goal;
    }

    public void setPlayerOnGoal(int line, int column)
    {
        _cases[line][column] = CaseContent.PlayerOnGoal;
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

    /**
     * Nom du niveau
     */
    public String getName()
    {
        return _name;
    }

    /**
     * renvoie vrai si la case à la ligne line et à la colonne column est vide
     */
    public boolean isClear(int l, int c)
    {
        return _cases[l][c] == null;
    }

    /**
     * renvoie vrai si la case à la ligne line et à la colonne column est un mur
     */
    public boolean isWall(int l, int c)
    {
        return _cases[l][c] == CaseContent.Wall;
    }

    /**
     * renvoie vrai si la case à la ligne line et à la colonne column est un but
     */
    public boolean isGoal(int l, int c)
    {
        return _cases[l][c] == CaseContent.Goal;
    }

    /**
     * renvoie vrai si la case à la ligne line et à la colonne column est un pousseur
     */
    public boolean isPlayer(int l, int c)
    {
        return _cases[l][c] == CaseContent.Player;
    }

    /**
     * renvoie vrai si la case à la ligne line et à la colonne column est une caisse
     */
    public boolean isBox(int l, int c)
    {
        return _cases[l][c] == CaseContent.Box;
    }

    public CaseContent getCase(int l, int c)
    {
        return _cases[l][c];
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

        sb.append("; ").append(_name).append("\n");
        return sb.toString();
    }
}

