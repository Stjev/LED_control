package com.stef.arduino.led_control.MVC.Model;

import android.bluetooth.BluetoothSocket;

public class BluetoothSocketModel extends AbstractModel {
    private BluetoothSocket socket;

    public BluetoothSocket getSocket() {
        return socket;
    }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
        fireInvalidationEvent();
    }
}
