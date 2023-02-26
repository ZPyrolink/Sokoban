package Managers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Settings
{
    public LogLevel logLevel = LogLevel.All;

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
