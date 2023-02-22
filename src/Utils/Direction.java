package Utils;

import java.awt.*;

public enum Direction
{
    //Null(0, 0),
    Up(0, -1),
    Right(1, 0),
    Down(0, 1),
    Left(-1, 0);

    public final Point value;

    Direction(int x, int y)
    {
        this.value = new Point(x, y);
    }

    public static Direction getDirection(Point p1, Point p2)
    {
        if (p1.equals(p2))
            return null;

        if (p1.x == p2.x)
        {
            return Integer.signum(p2.y- p1.y) == -1 ? Up : Down;
        }

        if (p1.y == p2.y)
        {
            return Integer.signum(p2.x- p1.x) == -1 ? Left : Right;
        }

        throw new RuntimeException();
    }
}
