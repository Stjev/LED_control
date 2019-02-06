package com.stef.arduino.led_control;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stef.arduino.led_control.Bluetooth.Bluetooth;
import com.stef.arduino.led_control.MVC.Controller.BluetoothListView;
import com.stef.arduino.led_control.MVC.Model.BluetoothDevicesModel;
import com.stef.arduino.led_control.MVC.View.BluetoothView;
import com.stef.arduino.led_control.MVC.Model.StatusModel;
import com.stef.arduino.led_control.MVC.Controller.LedSwitch;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter myBluetooth = null;

    private Bluetooth bluetooth;

    // CONTROLLERS
    private LedSwitch statusSwitch;
    private BluetoothListView listView;

    // MODELS
    private StatusModel statusModel;
    private BluetoothDevicesModel devicesModel;

    // VIEWS
    private BluetoothView bluetoothView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMVC();
    }

    private void initializeMVC(){
        statusModel = new StatusModel();
        devicesModel = new BluetoothDevicesModel();

        bluetoothView = new BluetoothView();

        bluetoothView.setStatusModel(statusModel);

        // find the switch
        statusSwitch = findViewById(R.id.led_status_switch);
        statusSwitch.setModel(statusModel);
        // find the listview
        listView = findViewById(R.id.lv_devices);
        listView.setModel(devicesModel);
        // once the bluetooth model is bound with the listview, activate bluetooth and find the devices.
        bluetooth = new Bluetooth(this, devicesModel);
    }
}
