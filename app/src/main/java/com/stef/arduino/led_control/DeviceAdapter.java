package com.stef.arduino.led_control;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DeviceAdapter extends ArrayAdapter<BluetoothDevice> {

    private final Context context;
    private List<BluetoothDevice> devices;

    public DeviceAdapter(Context context, int resource, List<BluetoothDevice> devices) {
        super(context, resource, devices);
        this.context = context;
        this.devices = devices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null) {
            vi = LayoutInflater.from(context).inflate(R.layout.device_adapter_view, null, false);
        }

        TextView name = vi.findViewById(R.id.device_name);
        TextView address = vi.findViewById(R.id.device_address);

        name.setText(devices.get(position).getName());
        address.setText(devices.get(position).getAddress());

        return vi;
    }
}
