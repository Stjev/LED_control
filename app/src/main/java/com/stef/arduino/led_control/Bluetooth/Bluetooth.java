package com.stef.arduino.led_control.Bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.stef.arduino.led_control.MVC.Model.BluetoothDevicesModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class Bluetooth {
    private Activity context; // this is needed for messages

    private final BluetoothAdapter mAdapter;
    private ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    private BluetoothDevicesModel model;

    public Bluetooth(Activity context, BluetoothDevicesModel model) {
        this.context = context;
        mAdapter = BluetoothAdapter.getDefaultAdapter();

        this.model = model;

        checkBluetoothOn();
        discoverDevices();
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

    private void discoverDevices(){
        Set<BluetoothDevice> bondedDevices = mAdapter.getBondedDevices();

        if(bondedDevices.size() > 0){
            Iterator<BluetoothDevice> iter = bondedDevices.iterator();

            while (iter.hasNext()) {
                model.addDevice(iter.next());
            }
        }
    }

    /**
     * This will set the model so the listview with devices can be updated whenever this finds a new device
     * @param model
     */
    public void setModel(BluetoothDevicesModel model){
        this.model = model;
    }
}
