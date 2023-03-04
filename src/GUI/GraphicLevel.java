package GUI;

import Model.CaseContent;
import Model.Game;
import Model.Level;
import Managers.Settings;
import Utils.Direction;
import Utils.GuiUtils;
import Utils.NumericUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


/**
 * Represent a GUI level
 */
@Deprecated(forRemoval = true)
public class GraphicLevel extends JComponent
{
    /**
     * Default size of a case
     */
    private static final int CASE_SIZE = 128;
    private int caseSizeFactorInverse = 2;

    private int getCaseSize()
    {
        return CASE_SIZE / caseSizeFactorInverse;
    }

    private static final String PLACEHOLDER = "%nb% moves";

    private final JLabel labelMoveNb;
    public int moveNb;

    public void setMoveNb(int value)
    {
        moveNb = value;
        labelMoveNb.setText(PLACEHOLDER.replace("%nb%", String.valueOf(moveNb)));
        labelMoveNb.setSize(labelMoveNb.getPreferredSize());
    }

    /**
     * Instantiate a GUI level.
     * <ul>
     *     <li>Add {@link MouseListener} and {@link KeyListener}</li>
     *     <li>{@link #requestFocus()}</li>
     * </ul>
     */
    public GraphicLevel()
    {
        super();

        labelMoveNb = new JLabel();
        Font tmp = labelMoveNb.getFont();
        labelMoveNb.setFont(new Font(tmp.getName(), tmp.getStyle(), 20));
        add(labelMoveNb);

        setMoveNb(0);
    }

    private Dimension getLevelSize(int caseSizeFactorInverse)
    {
        return new Dimension(
                Game.getGame().getCurrentLevel().getColumns() * (CASE_SIZE / caseSizeFactorInverse),
                Game.getGame().getCurrentLevel().getLines() * (CASE_SIZE / caseSizeFactorInverse) + 20);
    }

    /**
     * Set the size with the {@link Level#getColumns()} and {@link Level#getLines()} of {@link Game#getGame()}
     */
    private void setSize()
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension next = getLevelSize(caseSizeFactorInverse);

        if (GuiUtils.tooSmall(screen, next))
        {
            while (GuiUtils.tooSmall(screen, next))
            {
                caseSizeFactorInverse++;
                next = getLevelSize(caseSizeFactorInverse);
            }
        }
        else
        {
            while (!GuiUtils.tooSmall(screen,
                    getLevelSize(caseSizeFactorInverse - 1)) && caseSizeFactorInverse > 1)
            {
                caseSizeFactorInverse--;
                next = getLevelSize(caseSizeFactorInverse);
            }
        }

        Window root = GuiUtils.getRoot(this);
        root.setSize(next);
        root.setLocationRelativeTo(null);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D drawable = (Graphics2D) g;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);

        Level currentLevel = Game.getGame().getCurrentLevel();

        for (int l = 0; l < currentLevel.getLines(); l++)
            for (int c = 0; c < currentLevel.getColumns(); c++)
            {
                drawable.drawImage(CaseContent.getGroundSprite(), c * getCaseSize(), l * getCaseSize(),
                        getCaseSize(), getCaseSize(), null);

                CaseContent cc = currentLevel.getCase(l, c);
                if (cc != null)
                {
                    for (Image sprite : currentLevel.getCase(l, c).getSprites())
                        drawable.drawImage(sprite, c * getCaseSize(), l * getCaseSize(),
                                getCaseSize(), getCaseSize(), null);
                }
            }
    }

    @Override
    public void addNotify()
    {
        super.addNotify();

        setSize();
    }

    /**
     * Get the case coordinates where a mouse event have occured
     */
    public Point getCaseClicked(MouseEvent event)
    {
        return new Point(event.getX() / getCaseSize(), event.getY() / getCaseSize());
    }

    // ToDo: move to controllers

    /**
     * Check if the {@link Level#isFinished()} and go to the next
     */
    public void checkEnd()
    {
        if (!Game.getGame().getCurrentLevel().isFinished())
            return;

        if (!Settings.isFullScreen())
            JOptionPane.showMessageDialog(this, "Level finished!", "Victory",
                    JOptionPane.INFORMATION_MESSAGE);

        nextLevel();
    }

    /**
     * Go to the next level
     */
    public void nextLevel()
    {
        setMoveNb(0);

        if (Game.getGame().hasNext())
        {
            Game.getGame().next();
            GuiUtils.<Frame>getRoot(this).setTitle(Game.getGame().getCurrentLevel().getName());
            setSize();
            repaint();
        }
        else
        {
            if (!Settings.isFullScreen())
                JOptionPane.showMessageDialog(this, "Game finished!", "VICTORY",
                        JOptionPane.INFORMATION_MESSAGE);
            GuiUtils.getRoot(GraphicLevel.this).dispose();
        }
    }
}
