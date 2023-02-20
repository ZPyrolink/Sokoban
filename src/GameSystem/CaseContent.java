package GameSystem;

public enum CaseContent
{
    Wall('#'),
    Goal('.'),
    Player('@'),
    Box('$'),
    PlayerOnGoal('*');

    public final char Value;

    CaseContent(char value)
    {
        Value = value;
    }

    public static CaseContent FromValue(char value)
    {
        for (CaseContent cc : values())
            if (cc.Value == value)
                return cc;

        return null;
    }
}
