package com.stef.arduino.led_control.MVC.Controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.stef.arduino.led_control.LedMode;
import com.stef.arduino.led_control.MVC.Model.ModeModel;
import com.stef.arduino.led_control.R;

public class ModeSpinner extends android.support.v7.widget.AppCompatSpinner implements AdapterView.OnItemSelectedListener {

    private ModeModel model;
    private boolean initialized = false;

    public ModeSpinner(Context context) {
        super(context);
        initialize();
    }

    public ModeSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        // set the adapter for the spinner
        this.setAdapter(new ArrayAdapter<>(getContext(), R.layout.spinner_cell, LedMode.values()));
        // set color_cycling as the default
        this.setSelection(LedMode.COLOR_CYCLING.ordinal());

        this.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // the first time the spinner is still initializing, so data will try to be sent without a
        // bluetooth connection in place.
        if(! initialized) {
            initialized = true;
        } else if(model != null) model.setMode(LedMode.values()[position]);
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
