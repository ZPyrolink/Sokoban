package Controller;

import Abstract.AbstractController;
import Managers.Settings;
import Model.CaseContent;
import Model.Game;
import Model.Level;
import Utils.Direction;
import Utils.NumericUtils;
import View.LevelView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LevelController extends AbstractController<Level, LevelView>
{
    public LevelController(Level model)
    {
        super(model);
    }

    @Override
    public LevelView createView()
    {
        view = new LevelView(model);
        view.addMouseListener(new MouseListener());
        view.addKeyListener(new KeyListener());
        view.focus();
        return view;
    }

    public void setLevel(Level l)
    {
        model = l;
        view.setLevel(l);
    }

    //#region ToDo: move to the game controller

    /**
     * Check if the {@link Level#isFinished()} and go to the next
     */
    public void checkEnd()
    {
        if (!model.isFinished())
            return;

        if (!Settings.isFullScreen())
            view.showMessage("Level finished!", "Victory");

        nextLevel();
    }

    /**
     * Go to the next level
     */
    public void nextLevel()
    {
        view.resetMoveNb();

        if (Game.getGame().hasNext())
        {
            setLevel(Game.getGame().next());
            view.setTitle(model.getName());
            view.setSize();
            view.render();
        }
        else
        {
            if (!Settings.isFullScreen())
                view.showMessage("Game finished!", "VICTORY");

            view.disposeRoot();
        }
    }

    //#endregion

    //#region Listeners

    /**
     * Mouse listener to move player on click
     */
    private class MouseListener extends java.awt.event.MouseAdapter
    {
        /**
         * Get the case coordinates where a mouse event have occured
         */
        private Point getCaseClicked(MouseEvent event)
        {
            return new Point(event.getX() / view.getCaseSize(), event.getY() / view.getCaseSize());
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            Point nextCase = getCaseClicked(e);
            if (!model.canMove(nextCase))
                return;

            model.move(nextCase);
            view.incrementMoveNb();

            view.render();
            checkEnd();
        }
    }

    /**
     * Key listener.
     * <ul>
     *     <li>
     *         Move on {@link Settings.Key#UP}, {@link Settings.Key#DOWN},
     *         {@link Settings.Key#LEFT}, {@link Settings.Key#RIGHT} keys<
     *     /li>
     *     <li>Exit the application on {@link Settings.Key#EXIT} key</li>
     *     <li>Toggle full screen on {@link Settings.Key#FULL_SCREEN}</li>
     * </ul>
     */
    public class KeyListener extends java.awt.event.KeyAdapter
    {
        /**
         * Move the {@link CaseContent#Player} on a {@link Direction}
         */
        private void move(Direction d)
        {
            Point nextCase = d.value.getLocation();
            NumericUtils.translate(nextCase, model.getPlayerCo());
            if (!model.canMove(nextCase))
                return;

            model.move(nextCase);
            view.incrementMoveNb();

            view.render();
            checkEnd();
        }

        private void reset()
        {
            model.reset();
            view.resetMoveNb();
            view.render();
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            Settings.Key key = Settings.Key.of(e.getKeyCode());

            if (key == null)
                return;

            switch (key)
            {
                case UP -> move(Direction.Up);
                case DOWN -> move(Direction.Down);
                case LEFT -> move(Direction.Left);
                case RIGHT -> move(Direction.Right);

                case EXIT -> view.disposeRoot();
                case FULL_SCREEN -> view.toggleFullscreen();
                case RESET -> reset();

                // Debug: remove on release
                case DEBUG1 -> nextLevel();
                case DEBUG2 -> Settings.debugMode = !Settings.debugMode;
            }
            e.consume();
        }
    }

    //#endregion
}
