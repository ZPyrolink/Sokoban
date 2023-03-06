package View;

import Abstract.AbstractView;
import Model.Level;

public abstract class AbstractLevelView extends AbstractView<Level>
{
    protected AbstractLevelView(Level model)
    {
        super(model);
    }

    public void setLevel(Level l)
    {
        model = l;
    }

    public abstract void showMessage(String message, String title);

    public abstract int getCaseSize();

    public abstract void incrementMoveNb();

    public abstract void resetMoveNb();

    public abstract void disposeRoot();

    public abstract void toggleFullscreen();
}
