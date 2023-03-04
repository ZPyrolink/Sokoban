package GUI;

import Controller.LevelController;
import Model.CaseContent;
import Model.Game;
import Model.Level;

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
        super(Game.getGame().getCurrentLevel().getName());
        LevelController lc = new LevelController(Game.getGame().getCurrentLevel());
        add(lc.gl);
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
