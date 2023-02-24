package GUI;

import GameSystem.CaseContent;

import javax.swing.*;
import java.awt.*;

public class GraphicalInterface extends JFrame implements Runnable
{
    public GraphicalInterface()
    {
        super("Sokoban");
        add(new GraphicLevel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1_000, 700);
        setResizable(false);

        CaseContent.load();
    }

    private void setFullScreen()
    {
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(this);
    }

    @Override
    public void run()
    {
        setVisible(true);
        //setFullScreen();
    }
}
