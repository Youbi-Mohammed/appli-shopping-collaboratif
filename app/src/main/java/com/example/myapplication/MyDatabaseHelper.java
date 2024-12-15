package com.example.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    // Détails de la base de données
    private static final String DATABASE_NAME = "FruitVegetable.db";
    private static final int DATABASE_VERSION = 1;

    // Détails de la table
    private static final String TABLE_NAME = "fruit_vegetable_list";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "item_name";
    private static final String COLUMN_QUANTITY = "item_quantity";
    private static final String COLUMN_UNIT = "item_unit";
    private static final String COLUMN_EMOJI = "item_emoji";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer la table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT UNIQUE, " +
                COLUMN_QUANTITY + " INTEGER DEFAULT 0, " +
                COLUMN_UNIT + " TEXT DEFAULT 'g', " +
                COLUMN_EMOJI + " TEXT);";
        db.execSQL(createTableQuery);

        // Insérer les éléments initiaux
        insertInitialItems(db);
    }

    private void insertInitialItems(SQLiteDatabase db) {
        // Liste d'éléments à insérer
        String[][] items = {
                {"Apple", "🍎"}, {"Banana", "🍌"},
                {"Carrot", "🥕"}, {"Potato", "🥔"},
                {"Tomato", "🍅"}, {"Onion", "🧅"},

                // Produits laitiers
                {"Lait", "🥛"}, {"Fromage", "🧀"},
                {"Beurre", "🧈"}, {"Yaourt", "🍦"},
                {"Crème", "🍶"}, {"Glace", "🍨"},

                // Viande
                {"Poulet", "🍗"}, {"Bœuf", "🥩"},
                {"Poisson", "🐟"}, {"Saucisse", "\uD83C\uDF2D"},
                {"Agneau", "🐑"}, {"Dinde", "🦃"},

                // Riz, pâtes, céréales
                {"Riz", "🍚"}, {"Spaghettis", "🍝"},
                {"Céréales", "🥣"}, {"Pain complet", "🍞"},
                {"Farine", "🌾"}, {"Maïs", "🌽"},

                // Pains et pâtisseries
                {"Croissant", "🥐"}, {"Baguette", "🥖"},
                {"Pain au chocolat", "🍫"}, {"Brioche", "🍞"},
                {"Tarte", "🥧"}, {"Éclair", "🍩"}
        };

        // Boucle pour insérer chaque élément
        for (String[] item : items) {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, item[0]);
            cv.put(COLUMN_QUANTITY, 0);
            cv.put(COLUMN_UNIT, "g");
            cv.put(COLUMN_EMOJI, item[1]);
            db.insert(TABLE_NAME, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer la table existante si elle existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void updateOrInsertItem(String itemName, int quantity, String unit, String emoji) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, itemName);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_EMOJI, emoji);

        long id = db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update(TABLE_NAME, values, COLUMN_NAME + " = ?", new String[]{itemName});
        }
    }

    public void updateQuantity(String itemName, int quantity, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUANTITY, quantity);
        cv.put(COLUMN_UNIT, unit);

        db.update(TABLE_NAME, cv, COLUMN_NAME + "=?", new String[]{itemName});
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
