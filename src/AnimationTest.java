import Managers.Resource;

import javax.swing.*;
import java.awt.*;

public class AnimationTest
{
    public static void main(String[] args)
    {
        Image[] sprites = new Image[]
                {
                        Resource.Image.load("Pousseur/Pousseur_0_0")
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

                    g.drawImage(sprites[0], 0, 0, 100, 100, null);
                }
            };

            frame.add(comp);

            frame.setVisible(true);
        });
    }
}
