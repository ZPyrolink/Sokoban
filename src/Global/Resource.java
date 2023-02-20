package Global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public enum Resource
{
    Image("Images", "png");

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
}
