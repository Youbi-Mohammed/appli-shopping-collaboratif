package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.shopping_collab_project.R;

public class ItemsAdapter extends CursorAdapter {

    public ItemsAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the layout for each item in the grid view
        return LayoutInflater.from(context).inflate(R.layout.item_selected, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find the TextViews in the layout
        TextView itemName = view.findViewById(R.id.textView1);
        TextView itemEmoji = view.findViewById(R.id.imageView1);
        TextView itemQuantity = view.findViewById(R.id.textView11);

        // Get data from the cursor (these are the column names in your database)
        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("item_name"));
        @SuppressLint("Range") String emoji = cursor.getString(cursor.getColumnIndex("item_emoji"));
        @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("item_quantity"));
        @SuppressLint("Range") String unit = cursor.getString(cursor.getColumnIndex("item_unit"));

        // Set data in the views
        itemName.setText(name);
        itemEmoji.setText(emoji);

        // Concatenate quantity and unit and set it to the itemQuantity TextView
        itemQuantity.setText("Quantity: " + quantity + " " + unit);
    }
}
