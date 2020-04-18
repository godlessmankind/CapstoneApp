package com.humber.capstone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.humber.capstone.services.BluetoothConnectionService;

public class SendTextActivity extends AppCompatActivity {

    private static final String TAG = SendTextActivity.class.getSimpleName();

    private Button sendTextBtn;
    private EditText textToSend;

    Messenger mService = null;
    boolean bound;

//    private BluetoothConnectionService blueToothConnectionService;

//    private boolean isServiceBound = false;

 //   private ServiceConnection serviceConnection;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        textToSend = findViewById(R.id.text_to_send);
        sendTextBtn = findViewById(R.id.send_button);
        final Intent intent = new Intent(this, BluetoothConnectionService.class);
//        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);

        sendTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent sendTextIntent = new Intent(SendTextActivity.this,BluetoothConnectionService.class);
//                intent.putExtra("textData",textToSend.getText().toString());
//                startActivity(intent);
                if (!bound) return;
                // Create and send a message to the service, using a supported 'what' value
                Message msg = Message.obtain(null, 1, textToSend.getText().toString());
                try {
                    mService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, BluetoothConnectionService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (bound) {
            unbindService(mConnection);
            bound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            bound = true;
        }
        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            bound = false;
        }
    };

//    private void bindService(){
//        if(serviceConnection == null){
//            serviceConnection = new ServiceConnection() {
//                @Override
//                public void onServiceConnected(ComponentName name, IBinder service) {
//                    BluetoothConnectionService.BluetoothConnectionServiceBinder bluetoothConnectionServiceBinder = (BluetoothConnectionService.BluetoothConnectionServiceBinder) service;
//                    blueToothConnectionService=bluetoothConnectionServiceBinder.getService();
//                    isServiceBound = true;
//                }
//
//                @Override
//                public void onServiceDisconnected(ComponentName name) {
//                    isServiceBound = false;
//                }
//            };
//        }
//    }
//
//    private void unbindService(){
//        if(isServiceBound){
//            unbindService(serviceConnection);
//            isServiceBound=false;
//        }
//    }


}
