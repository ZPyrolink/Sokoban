import GUI.GraphicalInterface;
import Model.Game;
import Managers.Resource;

import javax.swing.*;

public class Sokoban
{
    public static void main(String[] args)
    {
        String levelsName = args.length >= 1 ? args[0] : "originals";
        Game.setGame(new Game(Resource.Game.open(levelsName)));
        Game.getGame().next();

        SwingUtilities.invokeLater(new GraphicalInterface());
    }
}