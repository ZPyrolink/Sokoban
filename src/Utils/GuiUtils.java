package Utils;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;

@UtilityClass
public class GuiUtils
{
    @SuppressWarnings("unchecked")
    public <T extends Window> T getRoot(Component c)
    {
        return (T) SwingUtilities.getRoot(c);
    }

    public void setFullScreen(Window window)
    {
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(window);
    }
}
