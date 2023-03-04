package View;

import Abstract.AbstractView;
import Managers.Settings;
import Model.CaseContent;
import Model.Level;
import Utils.GuiUtils;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class LevelView extends AbstractView<Level>
{
    //#region Case size

    /**
     * Default size of a case
     */
    private static final int CASE_SIZE = 128;
    private int caseSizeFactorInverse = 2;

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
                    model.getLevelSize(getCaseSize(caseSizeFactorInverse - 1))) && caseSizeFactorInverse > 1)
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

    public void setLevel(Level l)
    {
        model = l;
    }

    public LevelView(Level model)
    {
        super(model);

        moveNbLabel = new JLabel();
        Font tmp = moveNbLabel.getFont();
        moveNbLabel.setFont(new Font(tmp.getName(), tmp.getStyle(), 20));

        component = new Component();
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

    public void incrementMoveNb()
    {
        setMoveNb(moveNb + 1);
    }

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

    public void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(component, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void disposeRoot()
    {
        GuiUtils.getRoot(component).dispose();
    }

    public void focus()
    {
        component.setFocusable(true);
        component.requestFocus();
    }

    public void toggleFullscreen()
    {
        Settings.setFullScreen(GuiUtils.getRoot(component), !Settings.isFullScreen());
    }

    //#region Listeners

    public void addMouseListener(MouseListener listener)
    {
        component.addMouseListener(listener);
    }

    public void addKeyListener(KeyListener listener)
    {
        component.addKeyListener(listener);
    }

    //#endregion
}
