package com.stef.arduino.led_control.MVC.Model;

public class BrightnessModel extends AbstractModel {
    private short brightness;

    public BrightnessModel(){
        // this is supposed to be empty
        // brightness will automatically be set to 0
    }

    public BrightnessModel(short brightness) {
        this.brightness = brightness;
    }

    /**
     * Model
     */

    public short getBrightness(){ return brightness; }

    public void setBrightness(short brightness) {
        this.brightness = brightness;
        fireInvalidationEvent();
    }
}
