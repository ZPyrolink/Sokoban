package GUI;

import GameSystem.CaseContent;
import GameSystem.Game;
import GameSystem.Level;
import Managers.Settings;
import Utils.Direction;
import Utils.GuiUtils;
import Utils.NumericUtils;

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
                Game.getGame().getCurrentLevel().getColumns() * CASE_SIZE,
                Game.getGame().getCurrentLevel().getLines() * CASE_SIZE + 20);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D drawable = (Graphics2D) g;

        setSize();

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);

        Level currentLevel = Game.getGame().getCurrentLevel();

        for (int l = 0; l < currentLevel.getLines(); l++)
            for (int c = 0; c < currentLevel.getColumns(); c++)
            {
                drawable.drawImage(CaseContent.getGroundSprite(), c * CASE_SIZE, l * CASE_SIZE,
                        CASE_SIZE, CASE_SIZE, null);

                CaseContent cc = currentLevel.getCase(l, c);
                if (cc != null)
                {
                    for (Image sprite : currentLevel.getCase(l, c).getSprites())
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
        Level currentLevel = Game.getGame().getCurrentLevel();
        Point player = currentLevel.playerPosition();

        if (player.equals(nextCase))
            return false;

        if (!NumericUtils.singleSizeMoreLess(player, nextCase, 1))
            return false;

        CaseContent cc = currentLevel.getCase(nextCase);

        if (cc == null)
            return true;

        switch (cc)
        {
            case Goal:
                return true;
            case Box:
            case BoxOnGoal:
                Direction d = Direction.getDirection(player, nextCase);
                Point box = nextCase.getLocation();
                //noinspection ConstantConditions
                box.translate(d.value.x, d.value.y);

                cc = currentLevel.getCase(box);

                return cc == null || cc == CaseContent.Goal;

            default:
                return false;
        }
    }

    private void move(Point nextCase)
    {
        Level currentLevel = Game.getGame().getCurrentLevel();
        Point player = currentLevel.playerPosition();

        if (CaseContent.haveBox(currentLevel.getCase(nextCase)))
        {
            Direction d = Direction.getDirection(player, nextCase);
            Point box = nextCase.getLocation();
            //noinspection ConstantConditions
            box.translate(d.value.x, d.value.y);

            currentLevel.setCase(box, currentLevel.getCase(box) == null ? CaseContent.Box : CaseContent.BoxOnGoal);
        }

        switch (currentLevel.getCase(player))
        {
            case Player -> currentLevel.clearCase(player);
            case PlayerOnGoal -> currentLevel.setCase(player, CaseContent.Goal);
        }

        CaseContent cc = currentLevel.getCase(nextCase);
        CaseContent next = CaseContent.Player;

        if (CaseContent.haveGoal(cc))
            next = CaseContent.PlayerOnGoal;

        currentLevel.setCase(nextCase, next);

        repaint();
        checkEnd();
    }

    private void checkEnd()
    {
        if (!Game.getGame().getCurrentLevel().finished())
            return;

        JOptionPane.showMessageDialog(this, "Leveled finished!", "Victory",
                JOptionPane.INFORMATION_MESSAGE);

        Game.getGame().next();
        repaint();
    }

    private class MouseListener extends java.awt.event.MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            Point nextCase = getCaseClicked(e);
            if (!canMove(nextCase))
                return;

            move(nextCase);
        }
    }

    private class KeyListener extends java.awt.event.KeyAdapter
    {
        private void move(Direction d)
        {
            Point nextCase = d.value.getLocation();
            NumericUtils.translate(nextCase, Game.getGame().getCurrentLevel().playerPosition());
            if (!canMove(nextCase))
                return;

            GraphicLevel.this.move(nextCase);
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP -> move(Direction.Up);
                case KeyEvent.VK_DOWN -> move(Direction.Down);
                case KeyEvent.VK_LEFT -> move(Direction.Left);
                case KeyEvent.VK_RIGHT -> move(Direction.Right);

                case KeyEvent.VK_A, KeyEvent.VK_Q -> ((Window) SwingUtilities.getRoot(GraphicLevel.this)).dispose();
                case KeyEvent.VK_ESCAPE ->
                        Settings.setFullScreen(GuiUtils.getRoot(GraphicLevel.this), !Settings.isFullScreen());
            }
        }
    }
}
