package com.stef.arduino.led_control.MVC.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.stef.arduino.led_control.MVC.Model.ColorModel;

public class ColorPickerButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {

    private static final int COLOR_WHEEL_DENSITY = 12;

    private Context context;
    private AlertDialog colorDialog;

    private ColorModel model;

    public ColorPickerButton(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public ColorPickerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    private void initialize(){
        this.setOnClickListener(this);

        // Generate the color picker dialog
        colorDialog = ColorPickerDialogBuilder.with(context)
                .setTitle("Choose title")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(COLOR_WHEEL_DENSITY)
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        // Set the color in the model
                        if(model != null) { model.setColor(selectedColor); }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // this is intentionally empty
                    }
                }).build();
    }

    /**
     * Get the click event from this button
     * @param v this is an unused parameter
     */
    @Override
    public void onClick(View v) {
        colorDialog.show();
    }

    /**
     * Controller
     */

    public void setModel(ColorModel model) {
        this.model = model;
    }
}
