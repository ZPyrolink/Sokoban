package Abstract;

public abstract class AbstractController<TModel extends AbstractModel, TView extends AbstractView<TModel>>
{
    protected TModel model;
    protected TView view;

    protected AbstractController(TModel model)
    {
        this.model = model;
    }

    public TView createView()
    {
        return null;
    }
}
