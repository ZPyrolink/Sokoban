package GUI;

import GameSystem.Game;
import Managers.Settings;
import Utils.GuiUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class GraphicalMenu extends JMenuBar
{
    public GraphicalMenu()
    {
        add(new JMenu("Game")
        {{
            add(new JMenuItem("Toggle full-screen")
            {{
                setAccelerator(GraphicLevel.KeyListener.Key.FULL_SCREEN.getKeyStroke(0));
                addActionListener(e ->
                {
                    if (e.getModifiers() == KeyEvent.BUTTON1_MASK)
                        Settings.setFullScreen(GuiUtils.getRoot(GraphicalMenu.this), true);
                });
            }});
            add(new JMenuItem("Exit")
            {{
                setAccelerator(GraphicLevel.KeyListener.Key.EXIT.getKeyStroke(0));
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
                setAccelerator(GraphicLevel.KeyListener.Key.RESET.getKeyStroke(0));
                addActionListener(e ->
                {
                    if (e.getModifiers() == KeyEvent.BUTTON1_MASK)
                        Game.getGame().getCurrentLevel().reset();
                });
            }});
        }});
    }
}
