package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GraphicLevel extends JComponent
{
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D drawable = (Graphics2D) g;

        int width = getSize().width;
        int height = getSize().height;

        // On calcule le centre de la zone et un rayon
        Point center = new Point(width / 2, height / 2);

        Image img = null;

        try
        {
            img = ImageIO.read(new FileInputStream("Images/Pousseur.png"));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-2);
        }

        // On efface tout
        drawable.clearRect(0, 0, width, height);

        // On affiche une petite image au milieu
        drawable.drawImage(img, center.x - 20, center.y - 20, 40, 40, null);
    }
}
