package com.stef.arduino.led_control;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stef.arduino.led_control.Bluetooth.Bluetooth;
import com.stef.arduino.led_control.MVC.Controller.BluetoothListView;
import com.stef.arduino.led_control.MVC.Model.BluetoothDevicesModel;
import com.stef.arduino.led_control.MVC.Model.BluetoothRequestModel;
import com.stef.arduino.led_control.MVC.View.BluetoothView;
import com.stef.arduino.led_control.MVC.Model.StatusModel;
import com.stef.arduino.led_control.MVC.Controller.LedSwitch;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMVC();
    }

    private void initializeMVC(){
        // CONTROLLERS
        LedSwitch statusSwitch = findViewById(R.id.led_status_switch);
        BluetoothListView listView = findViewById(R.id.lv_devices);

        // MODELS
        StatusModel statusModel = new StatusModel();
        BluetoothDevicesModel devicesModel = new BluetoothDevicesModel();
        BluetoothRequestModel btRequestModel = new BluetoothRequestModel();

        // VIEWS
        BluetoothView bluetoothView = new BluetoothView();

        // Connect the models to the views and the controllers
        bluetoothView.setStatusModel(statusModel);

        // connect the switch controller
        statusSwitch.setModel(statusModel);
        // connect the listview controller
        listView.setDevicesModel(devicesModel);
        listView.setBluetoothRequestModel(btRequestModel);
        // once the bluetooth model is bound with the listview, activate bluetooth and find the devices.
        Bluetooth bluetooth = new Bluetooth(this, devicesModel);
        bluetooth.setDevicesModel(devicesModel);
        bluetooth.setRequestModel(btRequestModel);
        // Check if the bluetooth is turned on on this device and discover the devices
        bluetooth.checkBluetoothOn();
        bluetooth.discoverDevices();
    }
}
