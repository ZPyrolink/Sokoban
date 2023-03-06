package Controller;

import Abstract.AbstractController;
import Managers.Settings;
import Model.Game;
import View.GameView;

import javax.swing.*;

public class GameController extends AbstractController<Game, GameView>
{
    private final LevelController levelController;

    public GameController(Game model)
    {
        super(model);

        levelController = new LevelController(model.getCurrentLevel(), e -> nextLevel());
    }

    @Override
    public GameView createView()
    {
        view = new GameView(model,
                e -> Settings.setFullScreen(view.getFrame(), true),
                e -> view.getFrame().dispose(),
                e -> levelController.reset());
        view.add(levelController.createView().getComponent());
        return view;
    }

    /**
     * Go to the next level
     */
    private void nextLevel()
    {
        if (model.hasNext())
        {
            levelController.setLevel(model.next());
        }
        else
        {
            if (!Settings.isFullScreen())
                JOptionPane.showMessageDialog(view.getFrame(), "Game finished!", "VICTORY",
                        JOptionPane.INFORMATION_MESSAGE);

            view.getFrame().dispose();
        }
    }
}
