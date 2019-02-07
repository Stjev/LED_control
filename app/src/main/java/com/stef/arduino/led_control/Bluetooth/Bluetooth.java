package com.stef.arduino.led_control.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import com.stef.arduino.led_control.Interface.InvalidationListener;
import com.stef.arduino.led_control.Interface.Observable;
import com.stef.arduino.led_control.MVC.Model.BluetoothDevicesModel;
import com.stef.arduino.led_control.MVC.Model.BluetoothRequestModel;
import com.stef.arduino.led_control.MVC.Model.BluetoothSocketModel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Bluetooth implements InvalidationListener {
    private Activity context; // this is needed for messages

    private final BluetoothAdapter mAdapter;

    // MODELS
    private BluetoothDevicesModel devicesModel;
    private BluetoothRequestModel requestModel;
    private BluetoothSocketModel socketModel;

    public Bluetooth(Activity context, BluetoothDevicesModel model) {
        this.context = context;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * This will detect when the state has changed for the adapter and will then execute the discover device
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            discoverDevices();
        }
    };

    /**
     * Method to check whether the devices bluetooth is currently on. This will also try to detect
     * which bluetooth devices can be connected to.
     */
    public void checkBluetoothOn() {
        // Check if bluetooth is available and if it is enabled
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                Intent turnBon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivity(turnBon);

                // this will make sure the devices only get discovered when the adapter is actually on
                IntentFilter stateChangedFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                context.registerReceiver(mReceiver, stateChangedFilter);
            } else {
                discoverDevices();
            }
        } else {
            // The bluetooth device isn't available
            Toast.makeText(context, "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to discover the devices
     */
    private void discoverDevices(){
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

    public void setSocketModel(BluetoothSocketModel socketModel) {
        this.socketModel = socketModel;
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

        // If a correct socket is returned, alert the model
        if(socket != null) {
            socketModel.setSocket(socket);
        }
    }

    private BluetoothSocket connectToDevice() {
        ParcelUuid[] uuids = device.getUuids();
        BluetoothSocket socket;

        try {
            socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());

            // try to connect to the socket
            try {
                socket.connect();

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
            return null;
        }

        // send a message to let the user know the connection has been established
        Toast.makeText(context, "Connection established", Toast.LENGTH_SHORT).show();
        return socket;
    }
}
