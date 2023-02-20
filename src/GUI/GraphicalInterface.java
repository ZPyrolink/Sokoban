package GUI;

import GameSystem.CaseContent;
import Global.Logger;
import Global.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphicalInterface implements Runnable
{
    private static Map<CaseContent, Image> images;

    public static Map<CaseContent, Image> Images()
    {
        return images;
    }

    public static Image Image(CaseContent cc)
    {
        return images.get(cc);
    }

    private static Image ground;

    public static Image Ground()
    {
        return ground;
    }

    public Image loadImage(String name)
    {
        try
        {
            return ImageIO.read(Resource.Image.open(name));
        }
        catch (IOException e)
        {
            Logger.getInstance().throwException(e, "The image " + name + "doesn't exists!", -2);
            return null;
        }
    }

    public GraphicalInterface()
    {
        ground = loadImage("Sol");

        images = new HashMap<>()
        {{
            put(CaseContent.Wall, loadImage("Mur"));
            put(CaseContent.Goal, loadImage("But"));
            put(CaseContent.Player, loadImage("Pousseur"));
            put(CaseContent.Box, loadImage("Caisse"));
            put(CaseContent.PlayerOnGoal, loadImage("Caisse_sur_but"));
        }};
    }

    private JFrame frame;

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

        frame.setSize(1_000, 700);
        frame.setVisible(true);

        //setFullScreen();
    }
}
