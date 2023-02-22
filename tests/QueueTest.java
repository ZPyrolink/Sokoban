import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest
{
    // region Partie 3

    @Test
    public void test1()
    {
        var q = new Queue();
        assertEquals(q.size, 0);
    }

    @Test
    public void test2()
    {
        var q = new Queue();
        assertTrue(q.estVide());
    }

    @Test
    public void test3() throws FullQueueException
    {
        var f = new Queue();
        assertTrue(f.estVide());
        f.enfiler(1);
        assertFalse(f.estVide());
    }

    @Test
    public void test4() throws EmptyQueueException, FullQueueException
    {
        var f = new Queue();
        assertTrue(f.estVide());
        f.enfiler(1);
        assertFalse(f.estVide());
        f.defiler();
        assertTrue(f.estVide());
    }

    @Test
    public void test5() throws EmptyQueueException, FullQueueException
    {
        final int n = 5;
        int i;
        Queue f = new Queue();
        assertTrue(f.estVide());
        for (i = 0; i < n; i++)
        {
            f.enfiler(i);
            assertFalse(f.estVide());
        }
        while (i > 0)
        {
            assertFalse(f.estVide());
            f.defiler();
            i--;
        }
        assertTrue(f.estVide());
    }

    //endregion

    //region Partie 4

    @Test
    public void testValeurs0() throws FullQueueException
    {
        var f = new Queue();
        f.enfiler(1);
        assertEquals(f.tete(), 1);
    }

    @Test
    public void testValeurs1() throws EmptyQueueException, FullQueueException
    {
        var f = new Queue();
        f.enfiler(1);
        assertEquals(f.tete(), 1);
        f.defiler();
        f.enfiler(2);
        assertEquals(f.tete(), 2);
    }

    Queue f;

    @BeforeEach
    public void init()
    {
        f = new Queue();
    }

    @Test
    public void testVide() throws EmptyQueueException, FullQueueException
    {
        final int n = 5;
        int i;
        assertTrue(f.estVide());
        for (i = 0; i < n; i++)
        {
            f.enfiler(i);
            assertFalse(f.estVide());
        }
        while (i > 0)
        {
            assertFalse(f.estVide());
            f.defiler();
            i--;
        }
        assertTrue(f.estVide());
    }

    @Test
    public void testValeurs() throws EmptyQueueException, FullQueueException
    {
        f.enfiler(1);
        assertEquals(f.tete(), 1);
        f.defiler();
        f.enfiler(2);
        assertEquals(f.tete(), 2);
    }

    @Test
    public void testEtat0() throws EmptyQueueException, FullQueueException
    {
        f.enfiler(1);
        f.enfiler(2);
        assertEquals(f.tete(), 1);
        f.defiler();
        assertEquals(f.tete(), 2);
    }

    @Test
    public void testEtat1() throws EmptyQueueException, FullQueueException
    {
        int i;

        for (i = 0; i < 10; i++)
        {
            f.enfiler(1);
            f.enfiler(2);
            assertEquals(f.tete(), 1);
            f.defiler();
            assertEquals(f.tete(), 2);
            f.defiler();
        }
    }

    //endregion

    //region Partie 5

    @Test
    public void testExceptionVide()
    {
        assertThrows(EmptyQueueException.class, () -> f.defiler());
    }

    @Test
    public void testExceptionPleine() throws FullQueueException
    {
        int i;

        for (i = 0; i < Queue.CAPACITY; i++)
            f.enfiler(i);

        assertThrows(FullQueueException.class, () -> f.enfiler(Queue.CAPACITY));
    }

    //endregion
}
