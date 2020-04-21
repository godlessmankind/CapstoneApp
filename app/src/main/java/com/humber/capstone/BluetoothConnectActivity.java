package com.humber.capstone;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.humber.capstone.services.BluetoothConnectionService;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothConnectActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private ListView devicesLV;
    private Button refreshBtn;

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private BluetoothDeviceListAdapter deviceListAdapter;
    private BluetoothDeviceConfigInfo bluetoothDeviceConfig;

    Messenger mService = null;
    boolean bound;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bluetooth_connect);

        Button refreshBtn = findViewById(R.id.select_device_refresh_button);
        devicesLV = findViewById(R.id.select_devices_listview);

        final Intent startBluetoothServiceIntent = new Intent(getApplicationContext(), BluetoothConnectionService.class);

        checkBluetoothIsOK();
        bondedDevices();
        startBluetoothBonding();

        devicesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG,"onItemClick: Device was selected.");
                bluetoothAdapter.cancelDiscovery();
                String deviceName = bluetoothDevices.get(position).getName();
                String deviceAddress = bluetoothDevices.get(position).getAddress();
                Log.d(TAG,"onItemClick: deviceInfo: " + deviceName + " " + deviceAddress);

                Log.d(TAG,"onItemClick: Trying to pair to " + deviceName + ":" + deviceAddress);
                bluetoothDevices.get(position).createBond();
                startBluetoothServiceIntent.putExtra("bluetoothServerConfig",new BluetoothDeviceConfigInfo(deviceName,deviceAddress));
                startService(startBluetoothServiceIntent);
                sayHello();
//                stopService(serviceIntent);
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothDevices.clear();
                discoverDevices();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }



    private final BroadcastReceiver broadcastReceiverState = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)){
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,bluetoothAdapter.ERROR);
                    switch (state){
                        case BluetoothAdapter.STATE_OFF:
                            Log.d(TAG,"onReceive: STATE OFF");
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            Log.d(TAG,"broadcastReceiver: STATE TURNING OFF");
                            break;
                        case BluetoothAdapter.STATE_ON:
                            Log.d(TAG,"broadcastReceiver: STATE ON");
                            break;
                        case BluetoothAdapter.STATE_TURNING_ON:
                            Log.d(TAG,"broadcastReceiver: STATE TURNING ON");
                            break;
                    }
                }
            };

    };

    private final BroadcastReceiver broadcastReceiverDiscover = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG,"onReceive: ACTION FOUND.");
            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ":" + device.getAddress());
                deviceListAdapter = new BluetoothDeviceListAdapter(context, android.R.layout.simple_list_item_2 ,bluetoothDevices);
                devicesLV.setAdapter(deviceListAdapter);
            }
        }
    };

    private final BroadcastReceiver broadcastReceiverBonding = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG,"broadcastReceiverBonding: BOND_BONDED.");
                }
                if(device.getBondState() == BluetoothDevice.BOND_BONDING){
                    Log.d(TAG,"broadcastReceiverBonding: BOND_BONDING.");
                }
                if(device.getBondState() == BluetoothDevice.BOND_NONE){
                    Log.d(TAG,"broadcastReceiverBonding: BOND_NONE.");
                }
            }
        }
    };

    private void startBluetoothBonding(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(broadcastReceiverBonding, filter);
    }

    private void checkBluetoothIsOK(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_LONG).show();
        } else if(!bluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBluetoothIntent);
            IntentFilter BluetoothIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(broadcastReceiverState,BluetoothIntent);
        }
    }

    private void bondedDevices(){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        Log.d(TAG, "Number of paired devices: " + Integer.toString(pairedDevices.size()) );

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                bluetoothDevices.add(device);
                deviceListAdapter = new BluetoothDeviceListAdapter(this, android.R.layout.simple_list_item_2 ,bluetoothDevices);
                devicesLV.setAdapter(deviceListAdapter);
            }
        }
    }

    private void discoverDevices(){
        Log.i("BUTTON","REFRESH CLICKED");
        Log.d(TAG,"btnRefresh: Looking for unpaired devices.");

        checkBluetoothPermissions();

        if(bluetoothAdapter.isDiscovering()){
            Log.d(TAG,"discoverDevices: Canceling discovery.");
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG,"discoverDevices: Starting new discovery.");
            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(broadcastReceiverDiscover, discoverDevicesIntent);

        }else if(!bluetoothAdapter.isDiscovering()){
            Log.d(TAG,"discoverDevices: Starting discovery.");
            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(broadcastReceiverDiscover, discoverDevicesIntent);
        }
    }

    private void checkBluetoothPermissions() {
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {

            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "Connection with a service has been established");
            mService = new Messenger(service);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            bound = false;

        }
    };

    public void sayHello() {
        if (!bound) return;
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, 1, 0, 0);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


        @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroyed: called");
        super.onDestroy();
        try {
            unregisterReceiver(broadcastReceiverState);
        }catch (java.lang.IllegalArgumentException e){
            Log.e("onDestroy",e + " But I don't really give a shit!");
        }
        try {
            unregisterReceiver(broadcastReceiverDiscover);
        }catch (java.lang.IllegalArgumentException e){
            Log.e("onDestroy",e + " But I don't really give a shit!");
        }

        unregisterReceiver(broadcastReceiverBonding);
    }
}