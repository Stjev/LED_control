package com.stef.arduino.led_control.MVC.Model;

import com.stef.arduino.led_control.LedMode;

public class ModeModel extends AbstractModel {

    private LedMode mode;

    public ModeModel() {
        // this will be the default mode
        this(LedMode.COLOR_CYCLING);
    }

    public ModeModel(LedMode mode){
        this.mode = mode;
    }

    /**
     * Model
     */

    public LedMode getMode() {
        return mode;
    }

    public void setMode(LedMode mode) {
        this.mode = mode;
        fireInvalidationEvent();
    }
}
