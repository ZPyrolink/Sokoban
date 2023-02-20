package GUI;

import GameSystem.CaseContent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private static Image load(String imgPath) throws IOException
    {
        return ImageIO.read(new FileInputStream("Images/" + imgPath + ".png"));
    }

    public GraphicalInterface()
    {
        try
        {
            ground = load("Sol");

            images = new HashMap<>()
            {{
                put(CaseContent.Wall, load("Mur"));
                put(CaseContent.Goal, load("But"));
                put(CaseContent.Player, load("Pousseur"));
                put(CaseContent.Box, load("Caisse"));
                put(CaseContent.PlayerOnGoal, load("Caisse_sur_but"));
            }};
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
