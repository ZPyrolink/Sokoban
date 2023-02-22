package Utils;

public final class Settings
{
    public static LogLevel logLevel = LogLevel.All;

    public static final class LogLevel
    {
        public static final LogLevel None = new LogLevel(0),
                Error = new LogLevel(1 << 1),
                Log = new LogLevel(1 << 2),
                All = new LogLevel(Integer.MAX_VALUE);

        private final int value;

        private LogLevel(int value)
        {
            this.value = value;
        }

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

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
                return true;

            if (o == null || getClass() != o.getClass())
                return false;

            LogLevel logLevel = (LogLevel) o;
            return value == logLevel.value;
        }
    }
}
