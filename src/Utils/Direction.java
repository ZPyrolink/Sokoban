package Utils;

import java.awt.*;

/**
 * Represent a 2D vector
 */
public enum Direction
{
    /**
     * (0, 0)
     */
    Zero(0, 0),
    /**
     * (0, -1)
     */
    Up(0, -1),
    /**
     * (1, 0)
     */
    Right(1, 0),
    /**
     * (0, 1)
     */
    Down(0, 1),
    /**
     * (-1, 0)
     */
    Left(-1, 0);

    /**
     * Value of the vector
     */
    public final Point value;

    /**
     * Create a new direction with his coordinates
     */
    Direction(int x, int y)
    {
        this.value = new Point(x, y);
    }

    /**
     * Calculate the direction between to points
     *
     * @param p1 Origin point
     * @param p2 End point
     * @return The normalized vector from {@code p1} to {@code p2}
     * @throws RuntimeException Throwed if any of the axis isn't the same
     */
    public static Direction of(Point p1, Point p2)
    {
        if (p1.equals(p2))
            return null;

        if (p1.x == p2.x)
        {
            return Integer.signum(p2.y - p1.y) == -1 ? Up : Down;
        }

        if (p1.y == p2.y)
        {
            return Integer.signum(p2.x - p1.x) == -1 ? Left : Right;
        }

        throw new RuntimeException();
    }
}
