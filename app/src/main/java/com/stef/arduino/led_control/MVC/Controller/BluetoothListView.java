package com.stef.arduino.led_control.MVC.Controller;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stef.arduino.led_control.DeviceAdapter;
import com.stef.arduino.led_control.Interface.InvalidationListener;
import com.stef.arduino.led_control.Interface.Observable;
import com.stef.arduino.led_control.MVC.Model.BluetoothDevicesModel;

import java.util.ArrayList;

public class BluetoothListView extends ListView implements AdapterView.OnItemClickListener, InvalidationListener {

    private BluetoothDevicesModel model;
    private Context context;

    public BluetoothListView(Context context) {
        super(context);
        this.context = context;
        this.setOnItemClickListener(this);
    }

    public BluetoothListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setOnItemClickListener(this);
    }

    public void setModel(BluetoothDevicesModel model) {
        this.model = model;
        this.model.addListener(this);
    }

    /**
     * The onclickListener for the listview
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // print the clicked item
        System.out.println(((TextView) view).getText());
    }

    @Override
    public void invalidated(Observable observable) {
        ArrayList<BluetoothDevice> devices = model.getDevices();

        final DeviceAdapter adapter = new DeviceAdapter(context, android.R.layout.simple_list_item_1, devices);
        setAdapter(adapter);
    }
}
