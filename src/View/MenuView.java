package View;

import Managers.Settings;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuView extends JMenuBar
{
    public MenuView(
            ActionListener onFullScreen,
            ActionListener onExit,
            ActionListener onReset)
    {
        add(new JMenu("Game")
        {{
            add(new JMenuItem("Toggle full-screen")
            {{
                setAccelerator(Settings.Key.FULL_SCREEN.getKeyStroke(0));
                addActionListener(onFullScreen);
            }});
            add(new JMenuItem("Exit")
            {{
                setAccelerator(Settings.Key.EXIT.getKeyStroke(0));
                addActionListener(onExit);
            }});
        }});

        add(new JMenu("Level")
        {{
            add(new JMenuItem("Reset")
            {{
                setAccelerator(Settings.Key.RESET.getKeyStroke(0));
                addActionListener(onReset);
            }});
        }});
    }
}
