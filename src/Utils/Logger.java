package Utils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public final class Logger
{
    private static final Logger instance = new Logger();

    public static Logger getInstance()
    {
        return instance;
    }

    private final List<PrintStream> traceListeners;

    public void addTraceLsn(PrintStream stream)
    {
        traceListeners.add(stream);
    }

    public void clearTraceLsn()
    {
        traceListeners.clear();
    }

    private final List<PrintStream> errorListeners;

    public void addErrorLsn(PrintStream stream)
    {
        errorListeners.add(stream);
    }

    public void clearErrorLsn(PrintStream stream)
    {
        errorListeners.clear();
    }

    private Logger()
    {
        traceListeners = new ArrayList<>()
        {{
            add(System.out);
        }};

        errorListeners = new ArrayList<>()
        {{
            add(System.err);
        }};
    }

    public void log(String log)
    {
        if (Settings.logLevel.hasFlag(Settings.LogLevel.Log))
            traceListeners.forEach(tl -> tl.println(log));
    }

    public void logErr(String log)
    {
        if (Settings.logLevel.hasFlag(Settings.LogLevel.Log))
            errorListeners.forEach(el -> el.println(log));
    }

    public void throwException(Exception e, String message, int exitCode)
    {
        if (Settings.logLevel.hasFlag(Settings.LogLevel.Error))
            errorListeners.forEach(el ->
            {
                el.println(message);
                e.printStackTrace(el);
            });

        System.exit(exitCode);
    }

    public void throwException(Exception e, int exitCode)
    {
        if (Settings.logLevel.hasFlag(Settings.LogLevel.Error))
            errorListeners.forEach(e::printStackTrace);
        System.exit(exitCode);
    }
}
