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
        return view = new GameView(model, levelController.gl);
    }
}
