package View;

import Abstract.AbstractView;
import Model.CaseContent;
import Model.Game;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends AbstractView<Game>
{
    /**
     * Default size of the frame
     */
    private static final Dimension DEFAULT_SIZE = new Dimension(1_000, 700);

    @Getter
    private final JFrame frame;

    public GameView(Game model,
                    ActionListener onFullScreen,
                    ActionListener onExit,
                    ActionListener onReset,
                    LevelView lv)
    {
        super(model);

        frame = new JFrame(model.getCurrentLevel().getName());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEFAULT_SIZE);
        frame.setResizable(false);

        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(new MenuView(onFullScreen, onExit, onReset));

        frame.add(lv.getComponent());
    }

    @Override
    public void render()
    {
        CaseContent.load();
        frame.setVisible(true);
    }
}
