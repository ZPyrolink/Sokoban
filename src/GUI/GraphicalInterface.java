package GUI;

import Controller.LevelController;
import Model.CaseContent;
import Model.Game;
import Model.Level;
import View.MenuView;

import javax.swing.*;

/**
 * Represent the Sokoban Frame
 */
@Deprecated(forRemoval = true)
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void run()
    {
        setVisible(true);
    }
}
