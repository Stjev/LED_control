package com.stef.arduino.led_control.MVC.Controller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.stef.arduino.led_control.MVC.Model.BrightnessModel;

public class BrightnessSlider extends android.support.v7.widget.AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener {

    private Context context;
    private short brightness;
    private BrightnessModel model;

    public BrightnessSlider(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public BrightnessSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    private void initialize() {
        this.setOnSeekBarChangeListener(this);
        this.setMax(255);
        this.setProgress(255);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        brightness = (short)progress; // set the brightness to the progress
    }

    // Only update the model once the user is done tracking
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(model != null) model.setBrightness(brightness);
    }

    // This is not needed but has to be here for the Seekbar.OnSeekBarChangeListener interface
    @Override public void onStartTrackingTouch(SeekBar seekBar) {}

    /**
     * Controller
     */

    public void setModel(BrightnessModel model) {
        this.model = model;
    }
}
