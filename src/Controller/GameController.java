package Controller;

import Abstract.AbstractController;
import Managers.Settings;
import Model.Game;
import View.GameView;

public class GameController extends AbstractController<Game, GameView>
{
    private final LevelController levelController;

    public GameController(Game model)
    {
        super(model);

        levelController = new LevelController(model.getCurrentLevel());
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
}
