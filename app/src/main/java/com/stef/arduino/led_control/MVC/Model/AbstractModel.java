package com.stef.arduino.led_control.MVC.Model;

import com.stef.arduino.led_control.Interface.InvalidationListener;
import com.stef.arduino.led_control.Interface.Observable;

import java.util.HashSet;
import java.util.Set;


/**
 * This is the abstract version of the models, every model will at least have these methods
 */
public abstract class AbstractModel implements Observable {
    private final Set<InvalidationListener> listeners = new HashSet<>();

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void fireInvalidationEvent() {
        for (InvalidationListener listener : listeners) {
            listener.invalidated(this);
        }
    }
}
