package GUI;

import javax.swing.*;

public class GraphicalInterface implements Runnable
{
    @Override
    public void run()
    {
        var frame = new JFrame("Sokoban 5");
        frame.add(new GraphicLevel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
