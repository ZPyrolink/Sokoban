package GUI;

import GameSystem.CaseContent;
import GameSystem.Level;
import Programs.Program;

import javax.swing.*;
import java.awt.*;

public class GraphicLevel extends JComponent
{
    private static final int CASE_SIZE = 128 / 2;

    private void setSize()
    {
        SwingUtilities.getRoot(this).setSize(
                Program.getGame().currentLevel().getColumns() * CASE_SIZE,
                Program.getGame().currentLevel().getLines() * CASE_SIZE + 20);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D drawable = (Graphics2D) g;

        setSize();

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);

        Level currentLevel = Program.getGame().currentLevel();

        for (int l = 0; l < currentLevel.getLines(); l++)
            for (int c = 0; c < currentLevel.getColumns(); c++)
            {
                drawable.drawImage(CaseContent.GroundSprite(), c * CASE_SIZE, l * CASE_SIZE,
                        CASE_SIZE, CASE_SIZE, null);

                CaseContent cc = currentLevel.getCase(l, c);
                if (cc != null)
                {
                    drawable.drawImage(currentLevel.getCase(l, c).Sprite(),
                            c * CASE_SIZE, l * CASE_SIZE,
                            CASE_SIZE, CASE_SIZE, null);
                }
            }
    }
}
