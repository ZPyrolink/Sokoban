package GUI;

import GameSystem.Level;
import Programs.Program;

import javax.swing.*;
import java.awt.*;

public class GraphicLevel extends JComponent
{
    private static final int CASE_SIZE = 128 / 2;

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D drawable = (Graphics2D) g;

        int width = getSize().width;
        int height = getSize().height;

        Point center = new Point(width / 2, height / 2);

        drawable.clearRect(0, 0, width, height);

        Level currentLevel = Program.getGame().currentLevel();

        for (int l = 0; l < currentLevel.getLines(); l++)
            for (int c = 0; c < currentLevel.getColumns(); c++)
            {
                drawable.drawImage(GraphicalInterface.Ground(), c * CASE_SIZE, l * CASE_SIZE,
                        CASE_SIZE, CASE_SIZE, null);
                drawable.drawImage(GraphicalInterface.Image(currentLevel.getCase(l, c)),
                        c * CASE_SIZE, l * CASE_SIZE,
                        CASE_SIZE, CASE_SIZE, null);
            }
    }
}
