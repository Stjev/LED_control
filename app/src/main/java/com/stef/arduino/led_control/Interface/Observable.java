package com.stef.arduino.led_control.Interface;

public interface Observable {
    void addListener(InvalidationListener listener);
    void removeListener(InvalidationListener listener);
    void fireInvalidationEvent();
}
