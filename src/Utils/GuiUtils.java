package Utils;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;

/**
 * Class utility for swing GUI
 */
@UtilityClass
public class GuiUtils
{
    /**
     * Returns the root component for the current component tree.
     *
     * @param c   The component
     * @param <T> Type of the cast root
     * @return the first ancestor of c that's a Window
     * @throws RuntimeException the last ancestor is an Applet
     */
    @SuppressWarnings("unchecked")
    public <T extends Window> T getRoot(Component c)
    {
        return (T) getWindow(c);
    }

    /**
     * Returns the Windows component for the current component tree.
     *
     * @param c The component
     * @return the first ancestor of c that's a Window or the last Applet ancestor
     * @throws RuntimeException the last ancestor is an Applet
     */
    public Window getWindow(Component c)
    {
        Component result = SwingUtilities.getRoot(c);
        if (result instanceof Window w)
            return w;

        throw new RuntimeException();
    }

    /**
     * Set a windows on full screen
     */
    public void setFullScreen(Window window)
    {
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(window);
    }
}
