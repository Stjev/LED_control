package com.stef.arduino.led_control.MVC.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import com.stef.arduino.led_control.Interface.InvalidationListener;
import com.stef.arduino.led_control.Interface.Observable;
import com.stef.arduino.led_control.LedMode;
import com.stef.arduino.led_control.MVC.Model.BluetoothSocketModel;
import com.stef.arduino.led_control.MVC.Model.BrightnessModel;
import com.stef.arduino.led_control.MVC.Model.ModeModel;
import com.stef.arduino.led_control.MVC.Model.StatusModel;

import java.io.IOException;
import java.io.OutputStream;

public class BluetoothSenderView implements InvalidationListener {
    // MODELS
    private BrightnessModel brightnessModel;
    private ModeModel modeModel;
    private StatusModel statusModel;
    private BluetoothSocketModel socketModel;

    // VALUES (defaults are set)
    private short brightness;
    private boolean status;
    private LedMode mode;
    private BluetoothSocket socket = null;

    // This is used for displaying the error messages
    private Activity context;

    public BluetoothSenderView(Activity context) {
        this.context = context;
    }

    public void setBrightnessModel(BrightnessModel brightnessModel) {
        this.brightnessModel = brightnessModel;
        this.brightnessModel.addListener(this);
    }

    public void setModeModel(ModeModel modeModel) {
        this.modeModel = modeModel;
        this.modeModel.addListener(this);
    }

    public void setStatusModel(StatusModel statusModel) {
        this.statusModel = statusModel;
        this.statusModel.addListener(this);
    }

    public void setSocketModel(BluetoothSocketModel socketModel) {
        this.socketModel = socketModel;
        this.socketModel.addListener(this);
    }

    @Override
    public void invalidated(Observable observable) {
        // set the socket
        if(socket != socketModel.getSocket()) socket = socketModel.getSocket();
        else {
            if(socket == null || ! socket.isConnected()) {
                // Display an alert when there is no current connection
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("There is currently no device connected.")
                        .setTitle("Error")
                        .create().show();
            } else {
                if(brightness != brightnessModel.getBrightness()) brightness = brightnessModel.getBrightness();
                if(status != statusModel.getStatus()) status = statusModel.getStatus();
                if(mode != modeModel.getMode()) mode = modeModel.getMode();

                // If the leds are supposed to be off, set the mode to 0. Otherwise set the mode to
                // whatever is selected
                byte sendMode = (byte) (mode.ordinal());
                byte sendBrightness = (byte) ((status)? (brightness & 0xFF) : 0);

                try {
                    OutputStream ostream = socket.getOutputStream();

                    ostream.write(sendMode);
                    ostream.write(sendBrightness);
                } catch (IOException e) {
                    Toast.makeText(context, "There were some problems sending the data, please try again.", Toast.LENGTH_LONG).show();
                }

                // TODO: send the data of all the fields above
            }
        }
    }
}
