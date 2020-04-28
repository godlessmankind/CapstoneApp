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
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.capstone.database.AppDatabase;
import com.humber.capstone.model.Emoji;
import com.humber.capstone.services.BluetoothConnectionService;
import com.humber.capstone.adapters.EmojiAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmojiSelectActivity extends AppCompatActivity implements EmojiAdapter.OnItemClickListener{
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Emoji> emojiesDBList;
    private String mEmojiId;
    List<Emoji> emojiesList;

    private AppDatabase db;

    Messenger mService = null;
    boolean bound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);
        db = AppDatabase.getInstance(this);

//        db.emojiDao().deleteAll();
        int itemCount = db.emojiDao().getCount();
        if (itemCount == 0) {
            emojiesDBList = addEmojiesFromAssetsFolder("emojies");
            db.emojiDao().insertAll(emojiesDBList);
            Log.d(TAG,"onCreate: data inserted");
        }else {
            Log.d(TAG, "onCreate: data already there");
        }

        emojiesList = db.emojiDao().getAll();
        EmojiAdapter emojiAdapter = new EmojiAdapter(this,emojiesList,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.emojies_recycler_view);
        recyclerView.setAdapter(emojiAdapter);
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
        Message msg = Message.obtain(null, 2, mEmojiId);
        Log.d(TAG, "sayHello: " + mEmojiId);
        try {

            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    protected ArrayList<Emoji> addEmojiesFromAssetsFolder(String path){
        String [] fileList;
        ArrayList<Emoji> emojiesList = new ArrayList<Emoji>();
        try {
            fileList = getAssets().list(path);
            for (String file : fileList) {
                emojiesList.add(new Emoji(file.replace(".png",""), "faces",file));
                Log.d(TAG,file + "has been inserted in to the Database");
                }
        } catch (IOException e) {
            Log.d(TAG,e.toString());
        }
        return emojiesList;
    }

    @Override
    public void onClick(int position) {
        Emoji emoji = emojiesList.get(position);
        this.mEmojiId = Integer.toString(emoji.getId());
        Log.d(TAG, "onClick: " + mEmojiId);
        sayHello();
    }
}
