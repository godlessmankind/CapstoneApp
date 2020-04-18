package com.humber.capstone;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.capstone.database.DBHelper;
import com.humber.capstone.database.DataSource;
import com.humber.capstone.model.EmojiItem;

public class EmojiSelectActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter emojiAdapter;


    DataSource mDataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);

        mDataSource = new DataSource(this);
        mDataSource.open();




        SQLiteOpenHelper dbHelper = new DBHelper(this);
//        database = dbHelper.getWritableDatabase();
        Log.d(TAG,"database acquired!");


        recyclerView = findViewById(R.id.emojies_recycler_view);
        recyclerView.setHasFixedSize(true);


//        emojiAdapter = new ImageAdapter(getApplicationContext(), EmojiItem);
        recyclerView.setAdapter(emojiAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }
}
