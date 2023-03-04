package Abstract;

public abstract class AbstractView<TModel extends AbstractModel>
{
    protected TModel model;

    protected AbstractView(TModel model)
    {
        this.model = model;
    }

    protected abstract void render();
}
