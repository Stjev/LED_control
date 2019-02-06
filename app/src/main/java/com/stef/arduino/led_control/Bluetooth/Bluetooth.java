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

import static android.content.ContentValues.TAG;

public class Bluetooth {
    private Activity context; // this is needed for messages

    private final BluetoothAdapter mAdapter;
    private ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    private BluetoothDevicesModel model;

    public Bluetooth(Activity context) {
        this.context = context;
        mAdapter = BluetoothAdapter.getDefaultAdapter();

        checkBluetoothOn();
        discoverDevices();
    }

    public void checkBluetoothOn() {
        // Check if bluetooth is available and if it is enabled
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                Intent turnBon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivityForResult(turnBon, 1);
            }
        } else {
            // The bluetooth device isn't available
            Toast.makeText(context, "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
        }
    }

    private void discoverDevices(){
        // if the adapter is already in discovery mode, cancel the discovery
        if(mAdapter.isDiscovering()) {
            mAdapter.cancelDiscovery();
        }
        // check BT permissions in manifest
        checkBTPermissions();

        mAdapter.startDiscovery();
        IntentFilter discoveryDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, discoveryDevicesIntent);
    }

    /**
     * This will set the model so the listview with devices can be updated whenever this finds a new device
     * @param model
     */
    public void setModel(BluetoothDevicesModel model){
        this.model = model;
    }

    /**
     * Broadcast Receiver for listing devices that are not paired yet.
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "Discovering");

            // When discovery finds a device
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the bluetoothDevice object from the intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the device to the list of found devices
                model.addDevice(device);
                System.out.println("DEVICE FOUND: " + device.getName());
            }
        }
    };

    private void checkBTPermissions() {
        // This code will only execute if the version is above lollipop therefore it wont crash
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = context.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += context.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if(permissionCheck != 0) {
                context.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
            }
        }
    }
}
