package Controller;

import Abstract.AbstractController;
import Abstract.AbstractModel;
import Abstract.AbstractView;
import GUI.GraphicLevel;
import Managers.Settings;
import Model.CaseContent;
import Model.Game;
import Model.Level;
import Utils.Direction;
import Utils.GuiUtils;
import Utils.NumericUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class LevelController extends AbstractController<Level, AbstractView<Level>>
{
    public GraphicLevel gl;

    public LevelController(Level model)
    {
        super(model);
        gl = new GraphicLevel();
        gl.addMouseListener(new MouseListener());
        gl.addKeyListener(new KeyListener());
        gl.setFocusable(true);
        gl.requestFocus();
    }

    /**
     * Mouse listener to move player on click
     */
    private class MouseListener extends java.awt.event.MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            Point nextCase = gl.getCaseClicked(e);
            if (!Game.getGame().getCurrentLevel().canMove(nextCase))
                return;

            Game.getGame().getCurrentLevel().move(nextCase);
            gl.setMoveNb(gl.moveNb + 1);

            gl.repaint();
            gl.checkEnd();
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
            gl.setMoveNb(gl.moveNb + 1);

            gl.repaint();
            gl.checkEnd();
        }

        private void reset()
        {
            Game.getGame().getCurrentLevel().reset();
            gl.setMoveNb(0);
            gl.repaint();
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

                case EXIT -> GuiUtils.getRoot(gl).dispose();
                case FULL_SCREEN ->
                        Settings.setFullScreen(GuiUtils.getRoot(gl), !Settings.isFullScreen());
                case RESET -> reset();

                // Debug: remove on release
                case DEBUG1 -> gl.nextLevel();
                case DEBUG2 -> Settings.debugMode = !Settings.debugMode;
            }
            e.consume();
        }
    }
}
