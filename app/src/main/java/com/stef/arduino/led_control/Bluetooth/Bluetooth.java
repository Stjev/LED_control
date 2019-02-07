package com.stef.arduino.led_control.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import android.bluetooth.BluetoothAdapter;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import com.stef.arduino.led_control.Interface.InvalidationListener;
import com.stef.arduino.led_control.Interface.Observable;
import com.stef.arduino.led_control.MVC.Model.BluetoothDevicesModel;
import com.stef.arduino.led_control.MVC.Model.BluetoothRequestModel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;

public class Bluetooth implements InvalidationListener {
    private Activity context; // this is needed for messages

    private final BluetoothAdapter mAdapter;

    // MODELS
    private BluetoothDevicesModel devicesModel;
    private BluetoothRequestModel requestModel;

    public Bluetooth(Activity context, BluetoothDevicesModel model) {
        this.context = context;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void checkBluetoothOn() {
        // Check if bluetooth is available and if it is enabled
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                Intent turnBon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivity(turnBon);
            }
        } else {
            // The bluetooth device isn't available
            Toast.makeText(context, "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
        }
    }

    public void discoverDevices(){
        Set<BluetoothDevice> bondedDevices = mAdapter.getBondedDevices();

        if(bondedDevices.size() > 0){
            for (BluetoothDevice bondedDevice : bondedDevices) {
                devicesModel.addDevice(bondedDevice);
            }
        }
    }

    /**
     * This will set the devicesModel so the listview with devices can be updated whenever this finds a new device
     * @param devicesModel
     */
    public void setDevicesModel(BluetoothDevicesModel devicesModel){
        this.devicesModel = devicesModel;
    }

    /**
     * Invalidationlistener
     */

    private BluetoothDevice device;

    public void setRequestModel(BluetoothRequestModel requestModel) {
        this.requestModel = requestModel;
        this.requestModel.addListener(this);
    }

    @Override
    public void invalidated(Observable observable) {
        // If this isn't the same device as before, set this device to the new device
        if(device != requestModel.getDevice()) {
            device = requestModel.getDevice();
        }

        // Connect to this device
        BluetoothSocket socket = connectToDevice();
    }

    private BluetoothSocket connectToDevice() {
        ParcelUuid[] uuids = device.getUuids();
        BluetoothSocket socket = null;

        try {
            socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());

            // try to connect to the socket
            try {
                socket.connect();
                Toast.makeText(context, "Connection established", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                // This will attempt the fallback
                try {
                    socket = (BluetoothSocket)device.getClass().getMethod("createRfcommSocket", int.class).invoke(device,1);
                    socket.connect();
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e2) {
                    Toast.makeText(context, "Connecting to the selected device has failed. Please try again", Toast.LENGTH_LONG).show();
                    Log.d("", e2.getMessage());
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, "Error creating bluetooth socket.", Toast.LENGTH_LONG).show();
        }

        return socket;
    }
}
