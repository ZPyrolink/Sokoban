import Managers.Resource;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class AnimationTest
{
    private static double fpsToMsPF(int fps)
    {
        return TimeUnit.SECONDS.toMillis(1) / (double) fps;
    }

    private static Image[] sprites;
    private static int index;

    public static void main(String[] args)
    {
        sprites = new Image[]
                {
                        Resource.Image.load("Pousseur/Pousseur_0_0"),
                        Resource.Image.load("Pousseur/Pousseur_0_1"),
                        Resource.Image.load("Pousseur/Pousseur_0_2"),
                        Resource.Image.load("Pousseur/Pousseur_0_3")
                };

        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame("test");
            frame.setSize(500, 500);

            JComponent comp = new JComponent()
            {
                @Override
                public void paintComponent(Graphics g)
                {
                    int width = getSize().width;
                    int height = getSize().height;

                    g.clearRect(0, 0, width, height);

                    g.drawImage(sprites[index], 0, 0, 100, 100, null);
                }
            };

            frame.add(comp);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frame.setVisible(true);

            Timer t = new Timer((int) fpsToMsPF(10), e ->
            {
                if (++index >= sprites.length)
                    index = 0;

                comp.repaint();
            });

            t.start();
        });
    }
}
