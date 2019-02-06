package com.stef.arduino.led_control.MVC.Model;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

public class BluetoothDevicesModel extends AbstractModel {
    private ArrayList<BluetoothDevice> devices;

    public BluetoothDevicesModel(){
        this(new ArrayList<BluetoothDevice>());
    }

    public BluetoothDevicesModel(ArrayList<BluetoothDevice> devices) {
        this.devices = devices;
    }

    /**
     * Model for the bluetooth devices
     */

    public void addDevice(BluetoothDevice device) {
        devices.add(device);
        fireInvalidationEvent();
    }

    public void setDevices(ArrayList<BluetoothDevice> devices) {
        this.devices = devices;
        fireInvalidationEvent();
    }

    public void deleteDevice(BluetoothDevice device){
        devices.remove(device);
        fireInvalidationEvent();
    }

    public ArrayList<BluetoothDevice> getDevices(){
        return devices;
    }
}
