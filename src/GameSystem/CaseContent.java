package GameSystem;

import Managers.Resource;
import lombok.Getter;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Function;

public enum CaseContent
{
    Wall('#', "Mur"),
    Goal('.', "But"),
    Player('@', "Pousseur"),
    Box('$', "Caisse"),
    BoxOnGoal('*', "Caisse_sur_but"),
    PlayerOnGoal('+', "But", "Pousseur");

    @Getter
    private static Image groundSprite;

    public final char Value;
    private final String[] SpriteNames;
    @Getter
    private Image[] sprites;

    CaseContent(char value, String spriteName)
    {
        Value = value;
        SpriteNames = new String[] { spriteName };
    }

    CaseContent(char value, String... sprites)
    {
        Value = value;
        SpriteNames = sprites;
    }

    public static CaseContent of(char value)
    {
        for (CaseContent cc : values())
            if (cc.Value == value)
                return cc;

        return null;
    }

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

    public static boolean isPlayer(CaseContent cc)
    {
        return cc == Player || cc == PlayerOnGoal;
    }

    public static boolean isBox(CaseContent cc)
    {
        return cc == Box || cc == BoxOnGoal;
    }

    public static boolean isGoal(CaseContent cc)
    {
        return cc == Goal || cc == BoxOnGoal || cc == PlayerOnGoal;
    }
}
