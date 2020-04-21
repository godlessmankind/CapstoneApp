package com.humber.capstone.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "emojies")
public class Emoji extends ImageItem{



    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String itemName;
    @ColumnInfo
    public String category;
    @ColumnInfo
    public String image;

    public Emoji(String itemName,String category,String image){
        this.itemName = itemName;
        this.category = category;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName.replace("_", " ");
    }

    public String getImage() {
        return image;
    }



    @Override
    public String toString() {
        return "Emoji{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
