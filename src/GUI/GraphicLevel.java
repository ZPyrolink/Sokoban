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

/**
 * Represent a GUI level
 */
public class GraphicLevel extends JComponent
{
    /**
     * Default size of a case
     */
    private static final int CASE_SIZE = 128 / 2;

    /**
     * Instantiate a GUI level.
     * <ul>
     *     <li>Add {@link MouseListener} and {@link KeyListener}</li>
     *     <li>{@link #requestFocus()}</li>
     * </ul>
     */
    public GraphicLevel()
    {
        super();
        addMouseListener(new MouseListener());
        addKeyListener(new KeyListener());
        setFocusable(true);
        requestFocus();
    }

    /**
     * Set the size with the {@link Level#getColumns()} and {@link Level#getLines()} of {@link Game#getGame()}
     */
    private void setSize()
    {
        GuiUtils.getRoot(this).setSize(
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

    /**
     * Get the case coordinates where a mouse event have occured
     */
    private Point getCaseClicked(MouseEvent event)
    {
        return new Point(event.getX() / CASE_SIZE, event.getY() / CASE_SIZE);
    }

    /**
     * Check if the {@link CaseContent#Player} can move on another case
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean canMove(Point nextCase)
    {
        Level currentLevel = Game.getGame().getCurrentLevel();
        Point player = currentLevel.getPlayerCo();

        if (player.equals(nextCase))
            return false;

        if (!NumericUtils.singleAxisMoreLess(player, nextCase, 1))
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
                Direction d = Direction.of(player, nextCase);
                Point box = nextCase.getLocation();
                //noinspection ConstantConditions
                box.translate(d.value.x, d.value.y);

                cc = currentLevel.getCase(box);

                return cc == null || cc == CaseContent.Goal;

            default:
                return false;
        }
    }

    /**
     * Move the {@link CaseContent#Player} on the another case
     */
    private void move(Point nextCase)
    {
        Level currentLevel = Game.getGame().getCurrentLevel();
        Point player = currentLevel.getPlayerCo();

        if (CaseContent.haveBox(currentLevel.getCase(nextCase)))
        {
            Direction d = Direction.of(player, nextCase);
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

    /**
     * Check if the {@link Level#isFinished()} and go to the next
     */
    private void checkEnd()
    {
        if (!Game.getGame().getCurrentLevel().isFinished())
            return;

        JOptionPane.showMessageDialog(this, "Leveled finished!", "Victory",
                JOptionPane.INFORMATION_MESSAGE);

        Game.getGame().next();
        repaint();
    }

    /**
     * Mouse listener to move player on click
     */
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

    /**
     * Key listener.
     * <ul>
     *     <li>
     *         Move on {@link KeyListener#UP}, {@link KeyListener#DOWN},
     *         {@link KeyListener#LEFT}, {@link KeyListener#RIGHT} keys<
     *     /li>
     *     <li>Exit the application on {@link KeyListener#EXIT} key</li>
     *     <li>Toggle full screen on {@link KeyListener#FULL_SCREEN}</li>
     * </ul>
     */
    private class KeyListener extends java.awt.event.KeyAdapter
    {
        /**
         * Move the {@link CaseContent#Player} on a {@link Direction}
         */
        private void move(Direction d)
        {
            Point nextCase = d.value.getLocation();
            NumericUtils.translate(nextCase, Game.getGame().getCurrentLevel().getPlayerCo());
            if (!canMove(nextCase))
                return;

            GraphicLevel.this.move(nextCase);
        }

        /**
         * Key to move up
         */
        private static final int UP = KeyEvent.VK_UP;
        /**
         * Key to move down
         */
        private static final int DOWN = KeyEvent.VK_DOWN;
        /**
         * Key to move left
         */
        private static final int LEFT = KeyEvent.VK_LEFT;
        /**
         * Key to move right
         */
        private static final int RIGHT = KeyEvent.VK_RIGHT;

        /**
         * Key to exit the app
         */
        private static final int EXIT = KeyEvent.VK_Q;
        /**
         * Key to toggle full screen
         */
        private static final int FULL_SCREEN = KeyEvent.VK_ESCAPE;

        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case UP -> move(Direction.Up);
                case DOWN -> move(Direction.Down);
                case LEFT -> move(Direction.Left);
                case RIGHT -> move(Direction.Right);

                case EXIT -> ((Window) SwingUtilities.getRoot(GraphicLevel.this)).dispose();
                case FULL_SCREEN ->
                        Settings.setFullScreen(GuiUtils.getRoot(GraphicLevel.this), !Settings.isFullScreen());
            }
        }
    }
}
