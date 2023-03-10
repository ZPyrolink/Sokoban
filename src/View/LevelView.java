package View;

import Managers.Settings;
import Model.CaseContent;
import Model.Level;
import Utils.GuiUtils;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class LevelView extends AbstractLevelView
{
    //#region Case size

    /**
     * Default size of a case
     */
    private static final int CASE_SIZE = 128;
    private int caseSizeFactorInverse = 2;

    @Override
    public int getCaseSize()
    {
        return CASE_SIZE / caseSizeFactorInverse;
    }

    public int getCaseSize(int caseSizeFactorInverse)
    {
        return CASE_SIZE / caseSizeFactorInverse;
    }

    //#endregion

    //#region Component

    private class Component extends JComponent
    {
        public Component()
        {
            super();

            add(moveNbLabel);
            setMoveNb(0);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            Graphics2D drawable = (Graphics2D) g;

            int width = getSize().width;
            int height = getSize().height;

            drawable.clearRect(0, 0, width, height);

            for (int l = 0; l < model.getLines(); l++)
                for (int c = 0; c < model.getColumns(); c++)
                {
                    drawable.drawImage(CaseContent.getGroundSprite(), c * getCaseSize(), l * getCaseSize(),
                            getCaseSize(), getCaseSize(), null);

                    CaseContent cc = model.getCase(l, c);
                    if (cc != null)
                    {
                        for (Image sprite : model.getCase(l, c).getSprites())
                            drawable.drawImage(sprite, c * getCaseSize(), l * getCaseSize(),
                                    getCaseSize(), getCaseSize(), null);
                    }
                }
        }

        @Override
        public void addNotify()
        {
            super.addNotify();

            LevelView.this.setSize();
        }
    }

    @Getter
    private final Component component;

    /**
     * Set the size with the {@link Level#getColumns()} and {@link Level#getLines()} of {@link #model}
     */
    public void setSize()
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension next = model.getLevelSize(getCaseSize());

        if (GuiUtils.tooSmall(screen, next))
        {
            while (GuiUtils.tooSmall(screen, next))
            {
                caseSizeFactorInverse++;
                next = model.getLevelSize(getCaseSize());
            }
        }
        else
        {
            while (!GuiUtils.tooSmall(screen,
                    model.getLevelSize(getCaseSize(caseSizeFactorInverse - 1))) && caseSizeFactorInverse > 2)
            {
                caseSizeFactorInverse--;
                next = model.getLevelSize(getCaseSize());
            }
        }

        Window root = GuiUtils.getRoot(component);
        root.setSize(next);
        root.setLocationRelativeTo(null);
    }

    //#endregion

    @Override
    public void setLevel(Level l)
    {
        super.setLevel(l);

        resetMoveNb();
        setTitle(model.getName());
        setSize();
        render();
    }

    public LevelView(Level model, MouseListener ml, KeyListener kl)
    {
        super(model);

        moveNbLabel = new JLabel();
        Font tmp = moveNbLabel.getFont();
        moveNbLabel.setFont(new Font(tmp.getName(), tmp.getStyle(), 20));

        component = new Component();
        component.addMouseListener(ml);
        component.addKeyListener(kl);
        focus();
    }

    @Override
    public void render()
    {
        component.repaint();
    }

    public void setTitle(String newTitle)
    {
        GuiUtils.<Frame>getRoot(component).setTitle(newTitle);
    }

    //#region Move number

    private static final String PLACEHOLDER = "%nb% moves";

    private final JLabel moveNbLabel;

    private int moveNb;

    @Override
    public void incrementMoveNb()
    {
        setMoveNb(moveNb + 1);
    }

    @Override
    public void resetMoveNb()
    {
        setMoveNb(0);
    }

    private void setMoveNb(int value)
    {
        moveNb = value;
        moveNbLabel.setText(PLACEHOLDER.replace("%nb%", String.valueOf(moveNb)));
        moveNbLabel.setSize(moveNbLabel.getPreferredSize());
    }

    //#endregion

    @Override
    public void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(component, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void disposeRoot()
    {
        GuiUtils.getRoot(component).dispose();
    }

    private void focus()
    {
        component.setFocusable(true);
        component.requestFocus();
    }

    @Override
    public void toggleFullscreen()
    {
        Settings.setFullScreen(GuiUtils.getRoot(component), !Settings.isFullScreen());
    }
}
