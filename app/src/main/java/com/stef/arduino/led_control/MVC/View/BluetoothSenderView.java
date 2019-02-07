package com.stef.arduino.led_control.MVC.View;

import android.bluetooth.BluetoothSocket;

import com.stef.arduino.led_control.Interface.InvalidationListener;
import com.stef.arduino.led_control.Interface.Observable;
import com.stef.arduino.led_control.LedMode;
import com.stef.arduino.led_control.MVC.Model.BluetoothSocketModel;
import com.stef.arduino.led_control.MVC.Model.BrightnessModel;
import com.stef.arduino.led_control.MVC.Model.ModeModel;
import com.stef.arduino.led_control.MVC.Model.StatusModel;

public class BluetoothSenderView implements InvalidationListener {
    // MODELS
    private BrightnessModel brightnessModel;
    private ModeModel modeModel;
    private StatusModel statusModel;
    private BluetoothSocketModel socketModel;

    // VALUES
    private short brightness;
    private boolean status;
    private LedMode mode;
    private BluetoothSocket socket;

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
            if(brightness != brightnessModel.getBrightness()) brightness = brightnessModel.getBrightness();
            if(status != statusModel.getStatus()) status = statusModel.getStatus();
            if(mode != modeModel.getMode()) mode = modeModel.getMode();

            // TODO: send the data of all the fields above
        }
    }
}
