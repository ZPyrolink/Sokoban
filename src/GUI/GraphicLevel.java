package GUI;

import Model.CaseContent;
import Model.Game;
import Model.Level;
import Managers.Settings;
import Utils.Direction;
import Utils.GuiUtils;
import Utils.NumericUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


/**
 * Represent a GUI level
 */
@Deprecated(forRemoval = true)
public class GraphicLevel extends JComponent
{
    /**
     * Default size of a case
     */
    private static final int CASE_SIZE = 128;
    private int caseSizeFactorInverse = 2;

    private int getCaseSize()
    {
        return CASE_SIZE / caseSizeFactorInverse;
    }

    private static final String PLACEHOLDER = "%nb% moves";

    private final JLabel labelMoveNb;
    private int moveNb;

    private void setMoveNb(int value)
    {
        moveNb = value;
        labelMoveNb.setText(PLACEHOLDER.replace("%nb%", String.valueOf(moveNb)));
        labelMoveNb.setSize(labelMoveNb.getPreferredSize());
    }

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

        labelMoveNb = new JLabel();
        Font tmp = labelMoveNb.getFont();
        labelMoveNb.setFont(new Font(tmp.getName(), tmp.getStyle(), 20));
        add(labelMoveNb);

        setMoveNb(0);
    }

    private Dimension getLevelSize(int caseSizeFactorInverse)
    {
        return new Dimension(
                Game.getGame().getCurrentLevel().getColumns() * (CASE_SIZE / caseSizeFactorInverse),
                Game.getGame().getCurrentLevel().getLines() * (CASE_SIZE / caseSizeFactorInverse) + 20);
    }

    /**
     * Set the size with the {@link Level#getColumns()} and {@link Level#getLines()} of {@link Game#getGame()}
     */
    private void setSize()
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension next = getLevelSize(caseSizeFactorInverse);

        if (GuiUtils.tooSmall(screen, next))
        {
            while (GuiUtils.tooSmall(screen, next))
            {
                caseSizeFactorInverse++;
                next = getLevelSize(caseSizeFactorInverse);
            }
        }
        else
        {
            while (!GuiUtils.tooSmall(screen,
                    getLevelSize(caseSizeFactorInverse - 1)) && caseSizeFactorInverse > 1)
            {
                caseSizeFactorInverse--;
                next = getLevelSize(caseSizeFactorInverse);
            }
        }

        Window root = GuiUtils.getRoot(this);
        root.setSize(next);
        root.setLocationRelativeTo(null);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D drawable = (Graphics2D) g;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);

        Level currentLevel = Game.getGame().getCurrentLevel();

        for (int l = 0; l < currentLevel.getLines(); l++)
            for (int c = 0; c < currentLevel.getColumns(); c++)
            {
                drawable.drawImage(CaseContent.getGroundSprite(), c * getCaseSize(), l * getCaseSize(),
                        getCaseSize(), getCaseSize(), null);

                CaseContent cc = currentLevel.getCase(l, c);
                if (cc != null)
                {
                    for (Image sprite : currentLevel.getCase(l, c).getSprites())
                        drawable.drawImage(sprite, c * getCaseSize(), l * getCaseSize(),
                                getCaseSize(), getCaseSize(), null);
                }
            }
    }

    @Override
    public void addNotify()
    {
        super.addNotify();

        setSize();
    }

    /**
     * Get the case coordinates where a mouse event have occured
     */
    private Point getCaseClicked(MouseEvent event)
    {
        return new Point(event.getX() / getCaseSize(), event.getY() / getCaseSize());
    }

    // ToDo: move to controllers

    /**
     * Check if the {@link Level#isFinished()} and go to the next
     */
    private void checkEnd()
    {
        if (!Game.getGame().getCurrentLevel().isFinished())
            return;

        if (!Settings.isFullScreen())
            JOptionPane.showMessageDialog(this, "Level finished!", "Victory",
                    JOptionPane.INFORMATION_MESSAGE);

        nextLevel();
    }

    /**
     * Go to the next level
     */
    private void nextLevel()
    {
        setMoveNb(0);

        if (Game.getGame().hasNext())
        {
            Game.getGame().next();
            GuiUtils.<Frame>getRoot(this).setTitle(Game.getGame().getCurrentLevel().getName());
            setSize();
            repaint();
        }
        else
        {
            if (!Settings.isFullScreen())
                JOptionPane.showMessageDialog(this, "Game finished!", "VICTORY",
                        JOptionPane.INFORMATION_MESSAGE);
            GuiUtils.getRoot(GraphicLevel.this).dispose();
        }
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
            if (!Game.getGame().getCurrentLevel().canMove(nextCase))
                return;

            Game.getGame().getCurrentLevel().move(nextCase);
            setMoveNb(moveNb + 1);

            repaint();
            checkEnd();
        }
    }

    /**
     * Key listener.
     * <ul>
     *     <li>
     *         Move on {@link Key#UP}, {@link Key#DOWN},
     *         {@link Key#LEFT}, {@link Key#RIGHT} keys<
     *     /li>
     *     <li>Exit the application on {@link Key#EXIT} key</li>
     *     <li>Toggle full screen on {@link Key#FULL_SCREEN}</li>
     * </ul>
     */
    public class KeyListener extends java.awt.event.KeyAdapter
    {
        @AllArgsConstructor
        public enum Key
        {
            /**
             * Key to move up
             */
            UP(KeyEvent.VK_UP),
            /**
             * Key to move down
             */
            DOWN(KeyEvent.VK_DOWN),
            /**
             * Key to move left
             */
            LEFT(KeyEvent.VK_LEFT),
            /**
             * Key to move right
             */
            RIGHT(KeyEvent.VK_RIGHT),

            /**
             * Key to exit the app
             */
            EXIT(KeyEvent.VK_Q),
            /**
             * Key to toggle full screen
             */
            FULL_SCREEN(KeyEvent.VK_ESCAPE),
            /**
             * Key to reset the level
             */
            RESET(KeyEvent.VK_R),

            DEBUG1(KeyEvent.VK_F1),
            DEBUG2(KeyEvent.VK_F2);

            @SuppressWarnings("NonFinalFieldInEnum")
            @Getter
            private int value;

            public static Key of(int value)
            {
                return Arrays.stream(values()).filter(k -> k.value == value).findFirst().orElse(null);
            }

            public KeyStroke getKeyStroke(int modifiers)
            {
                return KeyStroke.getKeyStroke(value, modifiers, false);
            }
        }

        /**
         * Move the {@link CaseContent#Player} on a {@link Direction}
         */
        private void move(Direction d)
        {
            Point nextCase = d.value.getLocation();
            NumericUtils.translate(nextCase, Game.getGame().getCurrentLevel().getPlayerCo());
            if (!Game.getGame().getCurrentLevel().canMove(nextCase))
                return;

            Game.getGame().getCurrentLevel().move(nextCase);
            setMoveNb(moveNb + 1);

            repaint();
            checkEnd();
        }

        private void reset()
        {
            Game.getGame().getCurrentLevel().reset();
            setMoveNb(0);
            repaint();
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            Key key = Key.of(e.getKeyCode());

            if (key == null)
                return;

            switch (key)
            {
                case UP -> move(Direction.Up);
                case DOWN -> move(Direction.Down);
                case LEFT -> move(Direction.Left);
                case RIGHT -> move(Direction.Right);

                case EXIT -> GuiUtils.getRoot(GraphicLevel.this).dispose();
                case FULL_SCREEN ->
                        Settings.setFullScreen(GuiUtils.getRoot(GraphicLevel.this), !Settings.isFullScreen());
                case RESET -> reset();

                // Debug: remove on release
                case DEBUG1 -> nextLevel();
                case DEBUG2 -> Settings.debugMode = !Settings.debugMode;
            }
        }
    }
}
