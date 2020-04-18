package com.humber.capstone.database;

public class EmojiesTable {
    public static final String TABLE_ITEMS = "emojies";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_NAME = "itemName";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_POSITION = "sortPosition";
    public static final String COLUMN_IMAGE = "image";
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_POSITION + " INTEGER," +
                    COLUMN_IMAGE + " TEXT" + ");";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;
}