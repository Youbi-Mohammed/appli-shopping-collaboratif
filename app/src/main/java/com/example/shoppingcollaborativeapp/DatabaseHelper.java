package com.example.shoppingcollaborativeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shopping_app.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to store items
        String createTableQuery = "CREATE TABLE items (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, emoji TEXT, description TEXT, urgent BOOLEAN default false)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    // Method to insert a new item
    public void insertItem(String name, String emoji, String description, boolean isUrgent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("emoji", emoji);
        values.put("description", description);
        values.put("urgent", isUrgent);
        db.insert("items", null, values);
        db.close();
    }
}

