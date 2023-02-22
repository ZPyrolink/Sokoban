package Programs;

import GUI.GraphicalInterface;
import GameSystem.Game;
import Utils.Logger;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Program
{
    private static Game _game;

    public static Game getGame()
    {
        return _game;
    }

    public static void main(String[] args)
    {
        String levelsPath = args.length >=1 ? args[0] : "res/levels.txt";
        try
        {
            _game = new Game(new FileInputStream(levelsPath));
        }
        catch (FileNotFoundException e)
        {
            Logger.getInstance().throwException(e, "The levels file " + levelsPath + " doesn't exists!", -1);
        }
        _game.next();
        System.out.println(_game.currentLevel());

        SwingUtilities.invokeLater(new GraphicalInterface());
    }
}