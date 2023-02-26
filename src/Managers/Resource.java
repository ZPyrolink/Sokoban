package Managers;

import GameSystem.Game;
import lombok.AllArgsConstructor;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
public enum Resource
{
    Image("Images", "png"),
    Game("Levels", "txt");

    private final String path;
    private final String extension;

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
            Logger.throwException(e, "The " + resourcePath + " doesn't exists!", -3);
        }

        return null;
    }

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
