package GameSystem;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.PrintStream;

public class LevelWriter implements Closeable
{
    private PrintStream _stream;

    public LevelWriter(OutputStream stream)
    {
        _stream = new PrintStream(stream);
    }

    public void WriteLevel(Level level)
    {
        for (int line = 0; line < level.getLines(); line++)
        {
            for (int column = 0; column < level.getColumns(); column++)
            {
                char c = ' ';
                CaseContent cc = level.getCase(line, column);
                if (cc != null)
                    c = cc.Value;
                _stream.print(c);
            }
            _stream.println();
        }

        _stream.println("; " + level.getName() + "\n");
    }

    @Override
    public void close()
    {
        _stream.close();
    }
}
