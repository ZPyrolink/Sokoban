package View;

import Managers.Settings;
import Model.Game;
import Utils.GuiUtils;

import javax.swing.*;

public class MenuView extends JMenuBar
{
    public MenuView()
    {
        add(new JMenu("Game")
        {{
            add(new JMenuItem("Toggle full-screen")
            {{
                setAccelerator(Settings.Key.FULL_SCREEN.getKeyStroke(0));
                addActionListener(e -> Settings.setFullScreen(GuiUtils.getRoot(MenuView.this), true));
            }});
            add(new JMenuItem("Exit")
            {{
                setAccelerator(Settings.Key.EXIT.getKeyStroke(0));
                addActionListener(e -> GuiUtils.getRoot(MenuView.this).dispose());
            }});
        }});

        add(new JMenu("Level")
        {{
            add(new JMenuItem("Reset")
            {{
                setAccelerator(Settings.Key.RESET.getKeyStroke(0));
                addActionListener(e -> Game.getGame().getCurrentLevel().reset());
            }});
        }});
    }
}
