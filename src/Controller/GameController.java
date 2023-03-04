package Controller;

import Abstract.AbstractController;
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
        view = new GameView(model);
        view.add(levelController.createView().getComponent());
        return view;
    }
}
