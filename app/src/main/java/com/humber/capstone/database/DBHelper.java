package com.humber.capstone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_FILE_NAME = "capstone.db";
    public static final int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_FILE_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EmojiesTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EmojiesTable.SQL_CREATE);
        onCreate(db);
    }
}
