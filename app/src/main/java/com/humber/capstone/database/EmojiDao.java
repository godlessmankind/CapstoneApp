package com.humber.capstone.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.humber.capstone.model.Emoji;

import java.util.List;


@Dao
public interface EmojiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Emoji> item);

    @Query("SELECT * FROM emojies WHERE id = :id")
    Emoji getEmojiById(int id);

    @Query("SELECT * FROM emojies ORDER BY itemName ASC")
    List<Emoji> getAll();

    @Query("DELETE FROM emojies")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM emojies")
    int getCount();

}
