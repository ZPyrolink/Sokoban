package GUI;

import GameSystem.CaseContent;
import GameSystem.Level;
import Programs.Program;
import Utils.Utils;
import Utils.Direction;
import com.sun.java.accessibility.util.Translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GraphicLevel extends JComponent implements MouseListener
{
    private static final int CASE_SIZE = 128 / 2;

    public GraphicLevel()
    {
        super();
        addMouseListener(this);
    }

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

    private Point getCaseClicked(MouseEvent event)
    {
        return new Point(event.getX() / CASE_SIZE, event.getY() / CASE_SIZE);
    }

    private boolean canMove(Point newPosition)
    {
        Level currentLevel = Program.getGame().currentLevel();
        Point player = currentLevel.playerPosition();

        if (player.equals(newPosition))
            return false;

        if (!Utils.singleSizeMoreLess(player, newPosition, 1))
            return false;

        CaseContent cc = currentLevel.getCase(newPosition);

        if (cc == null)
            return true;

        if (cc != CaseContent.Box)
            return false;

        Direction d = Direction.getDirection(player, newPosition);
        Point box = newPosition.getLocation();
        box.translate(d.value.x, d.value.y);

        return currentLevel.getCase(box) == null;
    }

    //region MouseListener implementation

    @Override
    public void mouseClicked(MouseEvent e)
    {
        Point nextCase = getCaseClicked(e);
        if (!canMove(nextCase))
            return;

        Level currentLevel = Program.getGame().currentLevel();
        Point player = currentLevel.playerPosition();

        if (currentLevel.getCase(nextCase) == CaseContent.Box)
        {
            Direction d = Direction.getDirection(player, nextCase);
            Point box = nextCase.getLocation();
            box.translate(d.value.x, d.value.y);

            currentLevel.setCase(box, CaseContent.Box);
        }

        currentLevel.clearCase(player);
        currentLevel.setCase(nextCase, CaseContent.Player);

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    //endregion
}
