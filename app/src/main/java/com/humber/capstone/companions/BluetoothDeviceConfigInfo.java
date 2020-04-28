package com.humber.capstone.companions;

import android.os.Parcel;
import android.os.Parcelable;

public class BluetoothDeviceConfigInfo implements Parcelable {


    private String bluetoothDeviceName;
    private String bluetoothDeviceAddress;

    public static final Creator<BluetoothDeviceConfigInfo> CREATOR = new Creator<BluetoothDeviceConfigInfo>() {
        @Override
        public BluetoothDeviceConfigInfo createFromParcel(Parcel in) {
            return new BluetoothDeviceConfigInfo(in);
        }

        @Override
        public BluetoothDeviceConfigInfo[] newArray(int size) {
            return new BluetoothDeviceConfigInfo[size];
        }
    };

    public BluetoothDeviceConfigInfo(String bluetoothDeviceName, String bluetoothDeviceAddress) {
        this.bluetoothDeviceName = bluetoothDeviceName;
        this.bluetoothDeviceAddress = bluetoothDeviceAddress;
    }


    public String getBluetoothDeviceName() {
        return bluetoothDeviceName;
    }

    public void setBluetoothDeviceName(String bluetoothDeviceName) {
        this.bluetoothDeviceName = bluetoothDeviceName;
    }

    public String getBluetoothDeviceAddress() {
        return bluetoothDeviceAddress;
    }

    public void setBluetoothDeviceAddress(String bluetoothDeviceAddress) {
        this.bluetoothDeviceAddress = bluetoothDeviceAddress;
    }






    protected BluetoothDeviceConfigInfo(Parcel in) {
        bluetoothDeviceName = in.readString();
        bluetoothDeviceAddress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bluetoothDeviceName);
        dest.writeString(bluetoothDeviceAddress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "BluetoothDeviceConfig{" +
                "bluetoothDeviceName='" + bluetoothDeviceName + '\'' +
                ", bluetoothDeviceAddress='" + bluetoothDeviceAddress + '\'' +
                '}';
    }
}
