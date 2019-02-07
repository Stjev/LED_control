package com.stef.arduino.led_control.MVC.Model;

import android.bluetooth.BluetoothDevice;

public class BluetoothRequestModel extends AbstractModel {
    private BluetoothDevice device;

    public void setDevice(BluetoothDevice device) {
        this.device = device;
        fireInvalidationEvent();
    }

    public BluetoothDevice getDevice(){
        return device;
    }
}
