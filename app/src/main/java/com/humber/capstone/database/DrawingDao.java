package com.humber.capstone.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.humber.capstone.model.Drawing;

import java.util.List;

@Dao
public interface DrawingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Drawing> item);

    @Query("SELECT * FROM drawings WHERE id = :id")
    Drawing getDrawingById(int id);

    @Query("SELECT * FROM drawings ORDER BY id ASC")
    List<Drawing> getAll();

    @Query("DELETE FROM drawings")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM drawings")
    int getCount();
}
