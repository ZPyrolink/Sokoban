package Managers;

import lombok.experimental.UtilityClass;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/** Utility class for logs */
@UtilityClass
public class Logger
{
    /** Listeners used for logs */
    private final List<PrintStream> traceListeners = new ArrayList<>()
    {{
        add(System.out);
    }};

    /** Add a listener on {@link #traceListeners} */
    public void addTraceLsn(PrintStream stream)
    {
        traceListeners.add(stream);
    }
    /** Clear the {@link #traceListeners} */
    public void clearTraceLsn()
    {
        traceListeners.clear();
    }

    /** Listeners used for error */
    private final List<PrintStream> errorListeners = new ArrayList<>()
    {{
        add(System.err);
    }};

    /** Add a listener on {@link #errorListeners} */
    public void addErrorLsn(PrintStream stream)
    {
        errorListeners.add(stream);
    }
    /** Clear the {@link #errorListeners} */
    public void clearErrorLsn()
    {
        errorListeners.clear();
    }

    /** Log a message */
    public void log(String log)
    {
        if (Settings.logLevel.hasFlag(Settings.LogLevel.Log))
            traceListeners.forEach(tl -> tl.println(log));
    }

    /** Log an error */
    public void logErr(String log)
    {
        if (Settings.logLevel.hasFlag(Settings.LogLevel.Log))
            errorListeners.forEach(el -> el.println(log));
    }

    /**
     * Log a message
     * @param level Level of the log
     * @param log Message to log
     */
    public void log(Settings.LogLevel level, String log)
    {
        if (level.hasFlag(Settings.LogLevel.Log))
            log(log);

        if (level.hasFlag(Settings.LogLevel.Error))
            logErr(log);
    }

    /**
     * Log a message, print an exception and exit the app
     * @param e Exception to "throw"
     * @param message Additive error log
     * @param exitCode Return code of the app
     */
    public void throwException(Exception e, String message, int exitCode)
    {
        if (Settings.logLevel.hasFlag(Settings.LogLevel.Error))
            errorListeners.forEach(el ->
            {
                if (message != null)
                    el.println(message);
                e.printStackTrace(el);
            });

        System.exit(exitCode);
    }

    /**
     * Print an exception and exit the app
     * @param e Exception to "throw"
     * @param exitCode Return code of the app
     */
    public void throwException(Exception e, int exitCode)
    {
        throwException(e, null, exitCode);
    }
}
