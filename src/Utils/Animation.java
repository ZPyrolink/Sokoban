package Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Animation<T>
{
    private final T[] states;
    private int currentState;
    public T getCurrentState()
    {
        return states[currentState];
    }

    private final Timer timer;

    private final ActionListener listener;

    public Animation(T[] states, int timeBetweenStates, ActionListener onNextState)
    {
        this.states = states;
        currentState = 0;
        listener = onNextState;
        listener.actionPerformed(new ActionEvent(this, 0, null));

        timer = new Timer(timeBetweenStates, e ->
        {
            currentState++;
            listener.actionPerformed(new ActionEvent(this, 0, null));

            if (currentState >= states.length)
                ((Timer) e.getSource()).stop();
        });
        timer.start();
    }
}
