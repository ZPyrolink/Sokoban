package GUI;

import GameSystem.CaseContent;
import GameSystem.Game;

import javax.swing.*;
import java.awt.*;

public class GraphicalInterface extends JFrame implements Runnable
{
    public GraphicalInterface()
    {
        super(Game.getGame().currentLevel().getName());
        add(new GraphicLevel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1_000, 700);
        setResizable(false);

        setLocationRelativeTo(null);

        CaseContent.load();
    }

    @Override
    public void run()
    {
        setVisible(true);
    }
}
