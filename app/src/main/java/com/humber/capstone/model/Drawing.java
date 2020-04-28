package com.humber.capstone.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "drawings", indices = {@Index(value ={"image"}, unique = true)})
public class Drawing {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo
    public String image;

    public Drawing(String image){
        this.image = image;
    }

    public int getId() {
        return id;
    }


    public String getImage() {
        return image;
    }



    @Override
    public String toString() {
        return "Drawing{" +
                "id=" + id +
                ", image='" + image + '\'' +
                '}';
    }
}