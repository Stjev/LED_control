package com.stef.arduino.led_control.MVC.Model;

import android.graphics.Color;

public class ColorModel extends AbstractModel {
    private Color color;

    public ColorModel() {
        this(Color.WHITE);
    }

    public ColorModel(Color color) {
        this.color = color;
    }

    public ColorModel(int color) {
        this.color = Color.valueOf(color);
    }

    /**
     * MODEL
     */

    public void setColor(Color color) {
        this.color = color;
        fireInvalidationEvent();
    }

    public void setColor(int color) {
        this.color = Color.valueOf(color);
        fireInvalidationEvent();
    }

    public Color getColor(){
        return this.color;
    }
}
