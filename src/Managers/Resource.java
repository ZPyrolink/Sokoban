package Managers;

import Model.Game;
import lombok.AllArgsConstructor;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Resource manager
 */
@AllArgsConstructor
public enum Resource
{
    /**
     * Image resource (Images/*.png)
     */
    Image("Images", "png"),
    /**
     * Game resource (Levels/*.txt)
     */
    Game("Levels", "txt");

    /**
     * Folder of the resource
     */
    private final String path;
    /**
     * Extensoin of the resource
     */
    private final String extension;

    /**
     * Open a resource
     *
     * @param name      Name of the resource
     * @param extension Force the extension of the resrouce
     * @return The Stream reading the resource
     */
    public InputStream open(String name, String extension)
    {
        return openResource(path + "/" + name + "." + extension);
    }

    /**
     * Open a resource
     *
     * @param name Name of the resource
     * @return The Stream reading the resource
     */
    public InputStream open(String name)
    {
        return open(name, extension);
    }

    /**
     * Open a resource
     *
     * @param resourcePath Path of the resource
     * @return The Stream reading the resource
     */
    public static InputStream openResource(String resourcePath)
    {
        try
        {
            return new FileInputStream("res/" + resourcePath);
        }
        catch (FileNotFoundException e)
        {
            Logger.throwException(e, "The " + resourcePath + " doesn't exists!", -3);
        }

        return null;
    }

    /**
     * Load a resource
     *
     * @param name Name of the resource
     * @param <T>  Type of the resources.
     *             <table>
     *                <tr>
     *                    <th>{@link Resource}</th>
     *                    <th>{@code T}</th>
     *                </tr>
     *                <tr>
     *                    <td>{@link #Image}</td>
     *                    <td>{@link java.awt.Image}</td>
     *                </tr>
     *                <tr>
     *                    <td>{@link #Game}</td>
     *                    <td>{@link Game}</td>
     *                </tr>
     *             </table>
     * @return The resource loaded
     */
    @SuppressWarnings("unchecked")
    public <T> T load(String name)
    {
        T result = null;

        try
        {
            InputStream stream = open(name);

            switch (this)
            {
                case Image ->
                {
                    result = (T) ImageIO.read(open(name));
                    stream.close();
                }
                case Game -> result = (T) new Game(stream);
                default -> Logger
                        .throwException(new RuntimeException("Error loading Resource"), 4);
            }
        }
        catch (IOException e)
        {
            Logger.throwException(e, "Error loading " + name + " " + this + " resource!", -2);
        }

        return result;
    }
}
