package Utils;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.util.Arrays;

/**
 * Utility class for numeric values
 */
@UtilityClass
public final class NumericUtils
{
    /**
     * &plusmn between two values
     *
     * @param value     Value to check
     * @param comparer  Left value of the &plusmn tolerance
     * @param tolerance Right value of the &plusmn tolerance
     * @return {@code value} &isin {@code comparer} &plusmn {@code tolerance}
     */
    public static boolean moreLess(int value, int comparer, int tolerance)
    {
        return value >= comparer - tolerance && value <= comparer + tolerance;
    }

    /**
     * &plusmn between two coordinates
     *
     * @param value     Value to check
     * @param comparer  Left value of the &plusmn tolerance
     * @param tolerance Right value of the &plusmn tolerance
     * @return {@code value} &isin {@code comparer} &plusmn {@code tolerance}
     */
    public static boolean moreLess(Point value, Point comparer, int tolerance)
    {
        return moreLess(value.x, comparer.x, tolerance) && moreLess(value.y, comparer.y, tolerance);
    }

    /**
     * &plusmn between two coordinates on one axis
     *
     * @param value     Value to check
     * @param comparer  Left value of the &plusmn tolerance
     * @param tolerance Right value of the &plusmn tolerance
     * @return {@code value} &isin {@code comparer} &plusmn {@code tolerance}
     */
    public static boolean singleAxisMoreLess(Point value, Point comparer, int tolerance)
    {
        return value.equals(comparer) ||
                (value.x == comparer.x && moreLess(value.y, comparer.y, tolerance)) ||
                (value.y == comparer.y && moreLess(value.x, comparer.x, tolerance));
    }

    /**
     * Translates a point
     *
     * @param p1 Point to move
     * @param p2 The distance to move this point
     */
    public static void translate(Point p1, Point p2)
    {
        p1.translate(p2.x, p2.y);
    }

    /**
     * Copy an array into another
     */
    public static <T> void copy(T[][] array, T[][] result)
    {
        for (int i = 0; i < array.length; i++)
            result[i] = Arrays.copyOf(array[i], array[i].length);
    }
}
