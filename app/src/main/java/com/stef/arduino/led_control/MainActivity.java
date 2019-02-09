package com.stef.arduino.led_control;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.stef.arduino.led_control.Bluetooth.Bluetooth;
import com.stef.arduino.led_control.MVC.Controller.BluetoothListView;
import com.stef.arduino.led_control.MVC.Controller.BrightnessSlider;
import com.stef.arduino.led_control.MVC.Controller.ModeSpinner;
import com.stef.arduino.led_control.MVC.Model.BluetoothDevicesModel;
import com.stef.arduino.led_control.MVC.Model.BluetoothRequestModel;
import com.stef.arduino.led_control.MVC.Model.BluetoothSocketModel;
import com.stef.arduino.led_control.MVC.Model.BrightnessModel;
import com.stef.arduino.led_control.MVC.Model.ModeModel;
import com.stef.arduino.led_control.MVC.Model.StatusModel;
import com.stef.arduino.led_control.MVC.Controller.LedSwitch;
import com.stef.arduino.led_control.MVC.View.BluetoothSenderView;

public class MainActivity extends AppCompatActivity {

    // CONTROLLERS
    LedSwitch statusSwitch;
    //BluetoothListView listView;
    BrightnessSlider slider;
    ModeSpinner spinner;

    // MODELS
    StatusModel statusModel;
    BrightnessModel brightnessModel;
    ModeModel modeModel;
    BluetoothSocketModel socketModel;
    BluetoothDevicesModel devicesModel;
    BluetoothRequestModel btRequestModel;

    // VIEWS
    BluetoothSenderView senderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeControllers();
        initializeMVC();
    }

    private void initializeControllers() {
        statusSwitch = findViewById(R.id.led_status_switch);
        //listView = findViewById(R.id.lv_devices);
        slider = findViewById(R.id.slider_brightness);

        spinner = findViewById(R.id.mode_spinner);
        // set the adapter for the spinner
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_cell, LedMode.values()));
        // set color_cycling as the default
        spinner.setSelection(LedMode.COLOR_CYCLING.ordinal());
    }

    private void initializeMVC() {
        // Initialize the models
        statusModel     = new StatusModel();
        brightnessModel = new BrightnessModel();
        modeModel       = new ModeModel();
        socketModel     = new BluetoothSocketModel();
        devicesModel    = new BluetoothDevicesModel();
        btRequestModel  = new BluetoothRequestModel();

        // Initialize the views
        senderView = new BluetoothSenderView(this);

        // Set the default
        brightnessModel.setBrightness((short)255);
        statusModel.setStatus(true);
        modeModel.setMode(LedMode.COLOR_CYCLING);

        // Connect the models to the views and the controllers
        senderView.setStatusModel(statusModel);
        senderView.setBrightnessModel(brightnessModel);
        senderView.setModeModel(modeModel);
        senderView.setSocketModel(socketModel);

        // connect the switch controller
        statusSwitch.setModel(statusModel);

        // connect the listview controller
        //listView.setDevicesModel(devicesModel);
        //listView.setBluetoothRequestModel(btRequestModel);

        // connect the brightness slider to the model
        slider.setModel(brightnessModel);

        // connect the mode spinner to the mode model
        spinner.setModel(modeModel);

        // once the bluetooth model is bound with the listview, activate bluetooth and find the devices.
        Bluetooth bluetooth = new Bluetooth(this, devicesModel);
        bluetooth.setDevicesModel(devicesModel);
        bluetooth.setRequestModel(btRequestModel);
        bluetooth.setSocketModel(socketModel);

        // Check if the bluetooth is turned on on this device and discover the devices
        bluetooth.checkBluetoothOn();
    }
}
