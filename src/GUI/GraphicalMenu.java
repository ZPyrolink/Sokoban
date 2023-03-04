package GUI;

import Controller.LevelController;
import Model.Game;
import Managers.Settings;
import Utils.GuiUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;

@Deprecated(forRemoval = true)
public class GraphicalMenu extends JMenuBar
{
    public GraphicalMenu()
    {
        add(new JMenu("Game")
        {{
            add(new JMenuItem("Toggle full-screen")
            {{
                setAccelerator(LevelController.KeyListener.Key.FULL_SCREEN.getKeyStroke(0));
                addActionListener(e ->
                {
                    if (e.getModifiers() == KeyEvent.BUTTON1_MASK)
                        Settings.setFullScreen(GuiUtils.getRoot(GraphicalMenu.this), true);
                });
            }});
            add(new JMenuItem("Exit")
            {{
                setAccelerator(LevelController.KeyListener.Key.EXIT.getKeyStroke(0));
                addActionListener(e ->
                {
                    if (e.getModifiers() == KeyEvent.BUTTON1_MASK)
                        GuiUtils.getRoot(GraphicalMenu.this).dispose();
                });
            }});
        }});

        add(new JMenu("Level")
        {{
            add(new JMenuItem("Reset")
            {{
                setAccelerator(LevelController.KeyListener.Key.RESET.getKeyStroke(0));
                addActionListener(e ->
                {
                    if (e.getModifiers() == KeyEvent.BUTTON1_MASK)
                        Game.getGame().getCurrentLevel().reset();
                });
            }});
        }});
    }
}
