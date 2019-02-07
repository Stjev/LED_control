package com.stef.arduino.led_control.MVC.Controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.stef.arduino.led_control.Bluetooth.DeviceAdapter;
import com.stef.arduino.led_control.Interface.InvalidationListener;
import com.stef.arduino.led_control.Interface.Observable;
import com.stef.arduino.led_control.MVC.Model.BluetoothDevicesModel;
import com.stef.arduino.led_control.MVC.Model.BluetoothRequestModel;

public class BluetoothListView extends ListView implements AdapterView.OnItemClickListener, InvalidationListener {

    private BluetoothDevicesModel devicesModel;
    private BluetoothRequestModel requestModel;
    private Context context;
    private DeviceAdapter adapter;

    public BluetoothListView(Context context) {
        super(context);
        this.context = context;
        this.setOnItemClickListener(this);
        this.adapter = null;
    }

    public BluetoothListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setOnItemClickListener(this);
    }

    public void setDevicesModel(BluetoothDevicesModel devicesModel) {
        this.devicesModel = devicesModel;
        this.devicesModel.addListener(this);
    }

    public void setBluetoothRequestModel(BluetoothRequestModel requestModel) {
        this.requestModel = requestModel;
        this.requestModel.addListener(this);
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
        // Display an error message if the item can not be found
        if(adapter == null) {
            Toast.makeText(context, "Unable to get the clicked device.", Toast.LENGTH_LONG).show();
        } else {
            // The item can be found, and is on this position
            // give it to the model
            requestModel.setDevice(adapter.getItem(position));
        }
    }

    @Override
    public void invalidated(Observable observable) {
        // set the items of the devicesModel in the adapter
        adapter = new DeviceAdapter(context, android.R.layout.simple_list_item_1, devicesModel.getDevices());
        setAdapter(adapter);
    }
}
