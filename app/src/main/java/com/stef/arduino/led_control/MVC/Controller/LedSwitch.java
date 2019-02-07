package com.stef.arduino.led_control.MVC.Controller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.stef.arduino.led_control.MVC.Model.StatusModel;

public class LedSwitch extends Switch implements CompoundButton.OnCheckedChangeListener {

    private StatusModel model;

    public LedSwitch(Context context) {
        super(context);
        initialize();
    }

    public LedSwitch(Context context, AttributeSet attrs){
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        this.setOnCheckedChangeListener(this);
    }

    public void setModel(StatusModel model){
        this.model = model;
    }

    /**
     * When the button is pressed this event will be fired,
     * this will send the value of the switch to the model
     * @param isChecked the current value of the button
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.model.setStatus(isChecked);
    }
}
