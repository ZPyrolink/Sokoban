package Model;

import Managers.Resource;
import lombok.Getter;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Function;

/** Represent a game case */
public enum CaseContent
{
    /** Represent a wall. {@link #Player} / {@link #Box} cannot move on it */
    Wall('#', "Mur"),
    /** Represent a goal of the level. Put box on it to win */
    Goal('.', "But"),
    /** Represent the Player. Can push {@link #Box} */
    Player('@', "Pousseur"),
    /** Represent a Box. Can be moved with the {@link #Player} */
    Box('$', "Caisse"),
    /** {@link #Box} | {@link #Goal} */
    BoxOnGoal('*', "Caisse_sur_but"),
    /** {@link #Player} | {@link #Goal} */
    PlayerOnGoal('+', "But", "Pousseur");

    /** Sprite of the ground */
    @Getter
    private static Image groundSprite;

    /** Character of the case */
    public final char Value;
    /**
     * Names of the case sprites. See the
     * <a href="http://sokobano.de/wiki/index.php?title=Level_format">level format</a>
     */
    private final String[] SpriteNames;
    /** Sprites of the case. {@code sprites[0]} is the most below */
    @Getter
    private Image[] sprites;

    /** Create a new case with a single sprite */
    CaseContent(char value, String spriteName)
    {
        Value = value;
        SpriteNames = new String[] { spriteName };
    }

    /** Create a new case with multiple sprites */
    CaseContent(char value, String... sprites)
    {
        Value = value;
        SpriteNames = sprites;
    }

    /**
     * Get the case associated to a chacracter
     * @param value Character of the <a href=">
     */
    public static CaseContent of(char value)
    {
        for (CaseContent cc : values())
            if (cc.Value == value)
                return cc;

        return null;
    }

    /** Load the {@link #sprites} from {@link #SpriteNames} of all cases */
    public static void load()
    {
        for (CaseContent cc : values())
        {
            if (cc.SpriteNames.length == 0)
                continue;

            cc.sprites = Arrays.stream(cc.SpriteNames)
                    .map((Function<String, Image>) Resource.Image::load)
                    .toArray(Image[]::new);
        }

        groundSprite = Resource.Image.load("Sol");
    }

    /** cc && {@link #Player} == {@link #Player} */
    public static boolean havePlayer(CaseContent cc)
    {
        return cc == Player || cc == PlayerOnGoal;
    }

    /** cc && {@link #Box} == {@link #Box} */
    public static boolean haveBox(CaseContent cc)
    {
        return cc == Box || cc == BoxOnGoal;
    }

    /** cc && {@link #Goal} == {@link #Goal} */
    public static boolean haveGoal(CaseContent cc)
    {
        return cc == Goal || cc == BoxOnGoal || cc == PlayerOnGoal;
    }
}
