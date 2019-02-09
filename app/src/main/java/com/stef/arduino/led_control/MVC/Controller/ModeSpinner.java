package com.stef.arduino.led_control.MVC.Controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.stef.arduino.led_control.LedMode;
import com.stef.arduino.led_control.MVC.Model.ModeModel;

public class ModeSpinner extends android.support.v7.widget.AppCompatSpinner implements AdapterView.OnItemSelectedListener {

    private ModeModel model;

    public ModeSpinner(Context context) {
        super(context);
        initialize();
    }

    public ModeSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        this.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(model != null) model.setMode(LedMode.values()[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Intentionally empty, this won't change the current led mode
    }

    /**
     * Controller
     */

    public void setModel(ModeModel model) { this.model = model; }
}
