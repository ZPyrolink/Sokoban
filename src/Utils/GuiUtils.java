package Utils;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
     * @throws ClassCastException the root isn't of {@code T} type
     */
    @SuppressWarnings("unchecked")
    public <T extends Window> T getRoot(Component c)
    {
        Component result = SwingUtilities.getRoot(c);
        if (result instanceof Window w)
            return (T) w;

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

    public boolean tooSmall(Dimension container, Dimension content)
    {
        return container.width < content.width || container.height < content.height;
    }

    public ActionEvent emptyEvent(Object source)
    {
        return new ActionEvent(source, 0, null);
    }
}
