package Program;

import GUI.GraphicalInterface;
import GameSystem.Game;

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

    public static void main(String[] args) throws FileNotFoundException
    {
        _game = new Game(new FileInputStream(args.length >=1 ? args[0] : "levels.txt"));
        _game.next();
        System.out.println(_game.currentLevel());

        SwingUtilities.invokeLater(new GraphicalInterface());
    }
}