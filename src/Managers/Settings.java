package Managers;

import Utils.GuiUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class representing the current settings
 */
@UtilityClass
public class Settings
{
    /**
     * Current log level
     */
    @Getter
    @Setter
    public LogLevel logLevel = LogLevel.All;

    /**
     * Indicate if the game is currently in full screen
     */
    @Getter
    private boolean fullScreen;

    /**
     * Set the full screen mode
     *
     * @param window Current window (if set in full screen)
     * @param value  Value of the full screen
     */
    public void setFullScreen(Window window, boolean value)
    {
        fullScreen = value;
        GuiUtils.setFullScreen(value ? window : null);
        window.setLocationRelativeTo(null);
    }

    public boolean debugMode;

    @AllArgsConstructor
    public enum Key
    {
        /**
         * Key to move up
         */
        UP(KeyEvent.VK_UP),
        /**
         * Key to move down
         */
        DOWN(KeyEvent.VK_DOWN),
        /**
         * Key to move left
         */
        LEFT(KeyEvent.VK_LEFT),
        /**
         * Key to move right
         */
        RIGHT(KeyEvent.VK_RIGHT),

        /**
         * Key to exit the app
         */
        EXIT(KeyEvent.VK_Q),
        /**
         * Key to toggle full screen
         */
        FULL_SCREEN(KeyEvent.VK_ESCAPE),
        /**
         * Key to reset the level
         */
        RESET(KeyEvent.VK_R),

        DEBUG1(KeyEvent.VK_F1),
        DEBUG2(KeyEvent.VK_F2);

        @SuppressWarnings("NonFinalFieldInEnum")
        @Getter
        private int value;

        public static Key of(int value)
        {
            return Arrays.stream(values()).filter(k -> k.value == value).findFirst().orElse(null);
        }

        public KeyStroke getKeyStroke(int modifiers)
        {
            return KeyStroke.getKeyStroke(value, modifiers, false);
        }
    }

    /**
     * Flag enum representing the level of log
     */
    @EqualsAndHashCode(of = "value")
    @AllArgsConstructor
    public static final class LogLevel
    {
        /**
         * Don't show logs
         */
        public static final LogLevel None = new LogLevel(0);
        /**
         * Show errors
         */
        public static final LogLevel Error = new LogLevel(1 << 1);
        /**
         * Show logs
         */
        public static final LogLevel Log = new LogLevel(1 << 2);
        /**
         * Show all
         */
        public static final LogLevel All = new LogLevel(Integer.MAX_VALUE);

        /**
         * Flags cache
         */
        private static final Map<Integer, LogLevel> cache = new HashMap<>()
        {{
            put(None.value, None);
            put(Error.value, Error);
            put(Log.value, Log);
            put(All.value, All);
        }};

        /**
         * Value of the flag
         */
        private final int value;

        /**
         * Check if the value have the flag
         */
        public boolean hasFlag(LogLevel flag)
        {
            return (value & flag.value) == flag.value;
        }

        /**
         * Add a flag
         */
        public LogLevel addFlag(LogLevel flag)
        {
            int tmp = value | flag.value;
            LogLevel result = cache.getOrDefault(tmp, null);

            if (result == null)
            {
                result = new LogLevel(tmp);
                cache.put(tmp, result);
            }

            return result;
        }

        /**
         * Remove a flag
         */
        public LogLevel removeFlag(LogLevel flag)
        {
            int tmp = value & ~flag.value;
            LogLevel result = cache.getOrDefault(tmp, null);

            if (result == null)
            {
                result = new LogLevel(tmp);
                cache.put(tmp, result);
            }

            return result;
        }
    }
}
