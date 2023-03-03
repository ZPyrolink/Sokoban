package GUI;

import GameSystem.CaseContent;
import GameSystem.Game;
import GameSystem.Level;

import javax.swing.*;

/**
 * Represent the Sokoban Frame
 */
public class GraphicalInterface extends JFrame implements Runnable
{
    /**
     * Default width of the frame
     */
    private static final int DEFAULT_WIDTH = 1_000;
    /**
     * Default height of the frame
     */
    private static final int DEFAULT_HEIGHT = 700;

    /**
     * Instantiate the interface.
     * <ul>
     *     <li>Set the name with the {@link Level#getName()} of {@link Game#getGame()}</li>
     *     <li>Instantiate the {@link GraphicLevel}</li>
     *     <li>Use {@link #DEFAULT_WIDTH} and {@link #DEFAULT_HEIGHT} on {@link #setSize(int, int)} and disable resize</li>
     *     <li>{@link CaseContent#load()}</li>
     * </ul>
     */
    public GraphicalInterface()
    {
        super(Game.getGame().getCurrentLevel().getName());
        add(new GraphicLevel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);

        setLocationRelativeTo(null);

        setJMenuBar(new GraphicalMenu());

        CaseContent.load();
    }

    @Override
    public void run()
    {
        setVisible(true);
    }
}
