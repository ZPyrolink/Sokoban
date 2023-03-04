package Controller;

import Abstract.AbstractController;
import Model.Game;
import View.GameView;

public class GameController extends AbstractController<Game, GameView>
{
    public GameController(Game model)
    {
        super(model);
    }

    @Override
    public GameView createView()
    {
        return view = new GameView(model);
    }
}
