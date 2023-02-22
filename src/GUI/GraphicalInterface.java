package GUI;

import GameSystem.CaseContent;

import javax.swing.*;
import java.awt.*;

public class GraphicalInterface implements Runnable
{
    private JFrame frame;

    public GraphicalInterface()
    {
        CaseContent.loadImages();
    }

    private void setFullScreen()
    {
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(frame);
    }

    @Override
    public void run()
    {
        frame = new JFrame("Sokoban 5");
        frame.add(new GraphicLevel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1_216, 704 + 20);
        frame.setResizable(false);
        frame.setVisible(true);

        //setFullScreen();
    }
}
