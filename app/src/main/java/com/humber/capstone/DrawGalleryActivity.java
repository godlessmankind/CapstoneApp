package com.humber.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.humber.capstone.adapters.DrawingAdapter;
import com.humber.capstone.database.AppDatabase;
import com.humber.capstone.model.Drawing;
import com.humber.capstone.model.Emoji;
import com.humber.capstone.services.BluetoothConnectionService;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawGalleryActivity extends AppCompatActivity implements DrawingAdapter.OnItemClickListener{
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mImageAdapter;
    private ArrayList<Drawing> drawingsDBList;
    private String mDrawingId;
    List<Drawing> drawingsList;

    private AppDatabase db;

    Messenger mService = null;
    boolean bound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_gallery);
        db = AppDatabase.getInstance(this);
        drawingApp();


        db.drawingDao().deleteAll();
//        db.drawingDao().insertAll(drawingsDBList);
//        drawingsList = db.drawingDao().getAll();




    }

    public void drawingApp() {
        ImageButton drawApp = findViewById(R.id.drawing_app_button);
        drawApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrawGalleryActivity.this, DrawingAppActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, BluetoothConnectionService.class), mConnection, Context.BIND_AUTO_CREATE);
        drawingsDBList = addDrawingsFromDrawingsDir("Drawings");
        db.drawingDao().insertAll(drawingsDBList);
        drawingsList = db.drawingDao().getAll();
        DrawingAdapter drawingAdapter = new DrawingAdapter(this,drawingsList,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.drawings_recycler_view);
        recyclerView.setAdapter(drawingAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        Message msg = Message.obtain(null, 3, mDrawingId);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    protected ArrayList<Drawing> addDrawingsFromDrawingsDir(String path){
        File dir = new File(Environment.getExternalStorageDirectory() + "/Pictures/", path );
        if (!dir.exists()) {
            dir.mkdirs();
            Log.e(TAG, "Directory should be created");
        }else{
            Log.d(TAG, "addDrawingsFromDrawingsDir: directory is already there");
        }
        ArrayList<Drawing> drawingsList = new ArrayList<Drawing>();

        for (File file : dir.listFiles()) {
            drawingsList.add(new Drawing(file.toString()));
            Log.d(TAG,file + "has been inserted in to the Database");
        }
        return drawingsList;
    }

    @Override
    public void onClick(int position) {
        Drawing drawing = drawingsList.get(position);
        this.mDrawingId = Integer.toString(drawing.getId());
        Log.d(TAG, "onClick: " + mDrawingId);
        sayHello();
    }
}
