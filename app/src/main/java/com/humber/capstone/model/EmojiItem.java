package com.humber.capstone.model;

import android.content.ContentValues;

import com.humber.capstone.database.EmojiesTable;

import java.util.UUID;

public class EmojiItem {
    private String itemId;
    private String itemName;
    private String category;
    private int sortPosition;
    private String image;

    public EmojiItem() {
    }

    public EmojiItem(String itemId, String itemName, String category, int sortPosition, String image) {

        if(itemId == null){
            itemId = UUID.randomUUID().toString();
        }

        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.sortPosition = sortPosition;
        this.image = image;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ContentValues toValues(){
        ContentValues values = new ContentValues(5);
        values.put(EmojiesTable.COLUMN_ID,itemId);
        values.put(EmojiesTable.COLUMN_NAME,itemName);
        values.put(EmojiesTable.COLUMN_CATEGORY,category);
        values.put(EmojiesTable.COLUMN_POSITION,sortPosition);
        values.put(EmojiesTable.COLUMN_IMAGE,image);
        return values;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                ", sortPosition=" + sortPosition +
                ", Image='" + image + '\'' +
                '}';
    }
}
