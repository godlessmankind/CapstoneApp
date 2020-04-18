package com.humber.capstone;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BluetoothDeviceListAdapter extends ArrayAdapter<BluetoothDevice> {
    private LayoutInflater layoutInflater;
    private ArrayList<BluetoothDevice> devices;
    private int viewResourceId;

    BluetoothDeviceListAdapter(Context context, int textViewResourceId,  ArrayList<BluetoothDevice> devices) {
        super(context,  textViewResourceId, devices);
        this.devices = devices;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = textViewResourceId;
    }


    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = layoutInflater.inflate(viewResourceId, null);

        BluetoothDevice device = devices.get(position);

        if (device != null) {
            TextView deviceName = (TextView) convertView.findViewById(android.R.id.text1);
            TextView deviceAddress = (TextView) convertView.findViewById(android.R.id.text2);

            if (deviceName != null) {
                deviceName.setText(device.getName());
            }
            if (deviceAddress != null) {
                deviceAddress.setText(device.getAddress());
            }
        }

        return convertView;
    }

}