package Utils;

import GameSystem.Game;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public enum Resource
{
    Image("Images", "png"),
    Game("Levels", "txt");

    private final String path;
    private final String extension;

    Resource(String path, String extension)
    {
        this.path = path;
        this.extension = extension;
    }

    public InputStream open(String name, String extension)
    {
        return openResource(path + "/" + name + "." + extension);
    }

    public InputStream open(String name)
    {
        return open(name, extension);
    }

    public static InputStream openResource(String resourcePath)
    {
        try
        {
            return new FileInputStream("res/" + resourcePath);
        }
        catch (FileNotFoundException e)
        {
            Logger.getInstance().throwException(e, "The " + resourcePath + " doesn't exists!", -3);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T load(String name)
    {
        try (InputStream stream = open(name))
        {
            switch (this)
            {
                case Image:
                    return (T) ImageIO.read(stream);
                case Game:
                    return (T) new Game(stream);
                default:
                    Logger.getInstance()
                            .throwException(new RuntimeException("Error loading Resource"), 4);
            }
        }
        catch (IOException e)
        {
            Logger.getInstance().throwException(e, "Error loading " + name + " " + this + " resource!", -2);
        }

        return null;
    }
}
