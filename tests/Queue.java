class Queue
{
    public static final int CAPACITY = 10;

    int size;
    //int element;
    int[] elements;
    int tete;

    public Queue()
    {
        size = 0;
        elements = new int[CAPACITY];
        tete = 0;
    }

    public boolean estVide()
    {
        return size == 0;
    }

    public void enfiler(int e) throws FullQueueException
    {
        //element = e;
        if (size == 10)
            throw new FullQueueException();
        elements[(tete + size) % CAPACITY] = e;
        size++;
    }

    public void defiler() throws EmptyQueueException
    {
        if (size == 0)
            throw new EmptyQueueException("Impossible de défiler : file vide");

        size--;
        tete = (tete + 1) % CAPACITY;
    }

    public int tete()
    {
        return elements[tete];
    }
}