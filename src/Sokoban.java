import Controller.GameController;
import Managers.Resource;
import Model.Game;

import javax.swing.*;

public class Sokoban
{
    public static void main(String[] args)
    {
        String levelsName = args.length >= 1 ? args[0] : "originals";
        Game g = new Game(Resource.Game.open(levelsName));
        g.next();

        GameController gc = new GameController(g);
        SwingUtilities.invokeLater(() -> gc.createView().render());
    }
}