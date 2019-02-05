package com.stef.arduino.led_control;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.stef.arduino.led_control.Bluetooth.Bluetooth;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter myBluetooth = null;

    private Switch statusSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the switch
        statusSwitch = (Switch) findViewById(R.id.led_status_switch);

        initializeBluetooth();
        setSwitchEvent();
    }

    private void initializeBluetooth() {
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        // Check if bluetooth is available and if it is enabled
        if (myBluetooth != null) {
            if (!myBluetooth.isEnabled()) {
                Intent turnBon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBon, 1);
            }
        } else {
            // The bluetooth device isn't available
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
        }
    }

    private void setSwitchEvent(){
        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // turn the leds on
                    System.out.println("Switch is on");
                } else {
                    // turn the leds off
                    System.out.println("Switch is off");
                }
            }
        });
    }
}
