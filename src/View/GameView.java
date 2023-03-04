package View;

import Abstract.AbstractView;
import Controller.LevelController;
import GUI.GraphicLevel;
import Model.CaseContent;
import Model.Game;

import javax.swing.*;
import java.awt.*;

public class GameView extends AbstractView<Game>
{
    /**
     * Default size of the frame
     */
    private static final Dimension DEFAULT_SIZE = new Dimension(1_000, 700);

    private final JFrame frame;

    public GameView(Game model, GraphicLevel gl)
    {
        super(model);

        frame = new JFrame(model.getCurrentLevel().getName())
        {{
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(DEFAULT_SIZE);
            setResizable(false);

            setLocationRelativeTo(null);
            setJMenuBar(new MenuView());
        }};
        frame.add(gl);
    }

    @Override
    public void render()
    {
        CaseContent.load();
        frame.setVisible(true);
    }
}
