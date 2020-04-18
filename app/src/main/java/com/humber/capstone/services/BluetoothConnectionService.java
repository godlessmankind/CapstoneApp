package com.humber.capstone.services;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import com.humber.capstone.BluetoothDeviceConfigInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothConnectionService extends Service {
    private final String TAG = this.getClass().getSimpleName();

    private ConnectThread connection;


    private static final UUID MY_UUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");


//    private IBinder mBinder = new BluetoothConnectionServiceBinder();


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Bluetooth Connection Service has started");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"Bluetooth server has started");
        Bundle data = intent.getExtras();
        final BluetoothDeviceConfigInfo bluetoothConfig = data.getParcelable("bluetoothServerConfig");

        Log.d(TAG,"BT Server Command to Start an new Socket Connection");
        connection = new ConnectThread(bluetoothConfig);
        new Thread(connection).start();


        return super.onStartCommand(intent, flags, startId);
        }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Bluetooth Connection Service has stopped");
    }



    private class ConnectedThread{
        private final BluetoothSocket bluetoothSocket;
        private final OutputStream outputStream;

        public ConnectedThread(BluetoothSocket passedSocket){
            bluetoothSocket = passedSocket;

            OutputStream tmpOut = null;
            try{
                tmpOut = bluetoothSocket.getOutputStream();
            } catch (IOException e){
                Log.e(TAG, "Error occurred when creating output stream", e);
            }
            outputStream = tmpOut;
        }

        public void write(String data) {
            byte[] msgBuffer = data.getBytes();
            try {
                outputStream.write(msgBuffer);
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);
            }
        }

    }



    private class ConnectThread implements Runnable{
        private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        public BluetoothSocket bluetoothSocket;
        private final BluetoothDevice bluetoothDevice;
        public ConnectThread(BluetoothDeviceConfigInfo bluetoothConfig) {
            Log.d("TAG","Bluetooth Socket Thread Started");
            BluetoothSocket tmp = null;
            Log.d("TAG","Bluetooth will connect to the device with a name of "+ bluetoothConfig.getBluetoothDeviceName() + " and address of " + bluetoothConfig.getBluetoothDeviceAddress());
            bluetoothDevice = bluetoothAdapter.getRemoteDevice(bluetoothConfig.getBluetoothDeviceAddress());

            try {
                Log.d("TAG","Assigning the device UUID");
                tmp = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.d("TAG", "Socket's create() method failed", e);
            }
            bluetoothSocket = tmp;
        }
        public void run() {
            bluetoothAdapter.cancelDiscovery();
            try {
                Log.d("TAG","Trying to Connect");
                bluetoothSocket.connect();
                //isConnected = true;
            } catch (IOException connectException) {
                Log.d("TAG", "Can't connect ", connectException);
                try {
                    bluetoothSocket.close();
                } catch (IOException closeException) {
                    Log.e("TAG", "Could not close the client socket", closeException);
                }
                return;
            }
        }
        public void cancel() {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                Log.e("TAG", "Could not close the client socket", e);
            }
        }
    }



    private class IncomingHandler extends Handler {
        private Context applicationContext;

        IncomingHandler(Context context) {
          applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            String message = (String) msg.obj;
            switch(msg.what){
                case 1:
                    Log.d(TAG,"Text Data Received");
                    ConnectedThread sender = new ConnectedThread(connection.bluetoothSocket);
                    sender.write(message);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    Messenger messenger;
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        messenger = new Messenger(new IncomingHandler(this));
        return messenger.getBinder();
    }


}