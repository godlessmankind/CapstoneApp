package com.humber.capstone.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.humber.capstone.model.Emoji;

@Database(entities = {Emoji.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract EmojiDao emojiDao();

    public static AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"capstone-db").allowMainThreadQueries().build();
        }
        return instance;
    }

    public static void destroyInstance(){
        instance = null;
    }
}
