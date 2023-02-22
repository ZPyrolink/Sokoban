package GameSystem;

import Utils.Resource;

import java.awt.*;

public enum CaseContent
{
    Wall('#', "Mur"),
    Goal('.', "But"),
    Player('@', "Pousseur"),
    Box('$', "Caisse"),
    PlayerOnGoal('*', "Caisse_sur_but");

    private static Image groundSprite;
    public static Image GroundSprite()
    {
        return groundSprite;
    }

    public final char Value;
    private final String SpriteName;
    private Image sprite;
    public Image Sprite()
    {
        return sprite;
    }

    public static void loadImages()
    {
        for (CaseContent cc : values())
            cc.sprite = Resource.load(Resource.Image, cc.SpriteName);

        groundSprite = Resource.load(Resource.Image, "Sol");
    }

    CaseContent(char value, String spriteName)
    {
        Value = value;
        SpriteName = spriteName;
    }

    public static CaseContent FromValue(char value)
    {
        for (CaseContent cc : values())
            if (cc.Value == value)
                return cc;

        return null;
    }
}
