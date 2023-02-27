package Managers;

import Utils.GuiUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class Settings
{
    @Getter
    @Setter
    public LogLevel logLevel = LogLevel.All;

    @Getter
    private boolean fullScreen;
    public void setFullScreen(Window window, boolean value)
    {
        fullScreen = value;
        GuiUtils.setFullScreen(value ? window : null);
        window.setLocationRelativeTo(null);
    }

    @EqualsAndHashCode(of = "value")
    @AllArgsConstructor
    public static final class LogLevel
    {
        public static final LogLevel None = new LogLevel(0),
                Error = new LogLevel(1 << 1),
                Log = new LogLevel(1 << 2),
                All = new LogLevel(Integer.MAX_VALUE);

        private final int value;

        public boolean hasFlag(LogLevel flag)
        {
            return (value & flag.value) == flag.value;
        }

        public LogLevel addFlag(LogLevel flag)
        {
            return new LogLevel(value | flag.value);
        }

        public LogLevel removeFlag(LogLevel flag)
        {
            return new LogLevel(value & ~flag.value);
        }
    }
}
