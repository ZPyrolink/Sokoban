import GUI.GraphicalInterface;
import GameSystem.Game;
import Utils.Logger;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sokoban
{
    public static void main(String[] args)
    {
        String levelsPath = args.length >=1 ? args[0] : "res/levels.txt";
        try
        {
            Game.setGame(new Game(new FileInputStream(levelsPath)));
        }
        catch (FileNotFoundException e)
        {
            Logger.getInstance().throwException(e, "The levels file " + levelsPath + " doesn't exists!", -1);
        }
        Game.getGame().next();
        System.out.println(Game.getGame().currentLevel());

        SwingUtilities.invokeLater(new GraphicalInterface());
    }
}