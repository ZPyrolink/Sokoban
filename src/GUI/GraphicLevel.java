package GUI;

import GameSystem.CaseContent;
import GameSystem.Level;
import Programs.Program;
import Utils.Utils;
import Utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GraphicLevel extends JComponent
{
    private static final int CASE_SIZE = 128 / 2;

    public GraphicLevel()
    {
        super();
        addMouseListener(new MouseListener());
        addKeyListener(new KeyListener());
        setFocusable(true);
        requestFocus();
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
                    for (Image sprite : currentLevel.getCase(l, c).Sprite())
                        drawable.drawImage(sprite, c * CASE_SIZE, l * CASE_SIZE,
                                CASE_SIZE, CASE_SIZE, null);
                }
            }
    }

    private Point getCaseClicked(MouseEvent event)
    {
        return new Point(event.getX() / CASE_SIZE, event.getY() / CASE_SIZE);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean canMove(Point nextCase)
    {
        Level currentLevel = Program.getGame().currentLevel();
        Point player = currentLevel.playerPosition();

        if (player.equals(nextCase))
            return false;

        if (!Utils.singleSizeMoreLess(player, nextCase, 1))
            return false;

        CaseContent cc = currentLevel.getCase(nextCase);

        if (cc == null)
            return true;

        if (cc != CaseContent.Box)
            return false;

        Direction d = Direction.getDirection(player, nextCase);
        Point box = nextCase.getLocation();
        //noinspection ConstantConditions
        box.translate(d.value.x, d.value.y);

        cc = currentLevel.getCase(box);

        return cc == null || cc == CaseContent.Goal;
    }

    private void move(Point nextCase)
    {
        Level currentLevel = Program.getGame().currentLevel();
        Point player = currentLevel.playerPosition();

        if (currentLevel.getCase(nextCase) == CaseContent.Box)
        {
            Direction d = Direction.getDirection(player, nextCase);
            Point box = nextCase.getLocation();
            //noinspection ConstantConditions
            box.translate(d.value.x, d.value.y);

            currentLevel.setCase(box, currentLevel.getCase(box) == null ? CaseContent.Box : CaseContent.BoxOnGoal);
        }

        currentLevel.clearCase(player);
        currentLevel.setCase(nextCase, CaseContent.Player);

        repaint();
    }

    private class MouseListener implements java.awt.event.MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            Point nextCase = getCaseClicked(e);
            if (!canMove(nextCase))
                return;

            move(nextCase);
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
    }

    private class KeyListener implements java.awt.event.KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            Direction direction = switch (e.getKeyCode())
                    {
                        case KeyEvent.VK_UP -> Direction.Up;
                        case KeyEvent.VK_DOWN -> Direction.Down;
                        case KeyEvent.VK_LEFT -> Direction.Left;
                        case KeyEvent.VK_RIGHT -> Direction.Right;

                        default -> null;
                    };

            if (direction == null)
                return;


            Point nextCase = direction.value.getLocation();
            Utils.translate(nextCase, Program.getGame().currentLevel().playerPosition());
            if (!canMove(nextCase))
                return;
            move(nextCase);
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
        }
    }
}
