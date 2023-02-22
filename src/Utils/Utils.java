package Utils;

import java.awt.*;

public final class Utils
{
    private Utils(){}

    public static boolean moreLess(int value1, int value2, int tolerance)
    {
        return value1 >= value2 - tolerance && value1 <= value2 + tolerance;
    }

    public static boolean moreLess(Point value1, Point value2, int tolerance)
    {
        return moreLess(value1.x, value2.x, tolerance) && moreLess(value1.y, value2.y, tolerance);
    }

    public static boolean singleSizeMoreLess(Point value1, Point value2, int tolerance)
    {
        return value1.equals(value2) ||
                (value1.x == value2.x && moreLess(value1.y, value2.y, tolerance)) ||
                (value1.y == value2.y && moreLess(value1.x, value2.x, tolerance));
    }
}
