package com.humber.capstone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.humber.capstone.model.EmojiItem;

public class DataSource {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open(){
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close(){
        mDbHelper.close();
    }

    public EmojiItem createEmoji(EmojiItem emoji){
        ContentValues values = emoji.toValues();
        mDatabase.insert(EmojiesTable.TABLE_ITEMS, null,values);
        return emoji;
    }

//    public long getDataitemsCount()



}
