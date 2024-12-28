package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_collab_project.R;


public class ItemSelectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);
        // Get a reference to the GridView
        GridView gridView = findViewById(R.id.gridView);

        // Get a cursor with items where quantity is greater than 0
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        Cursor cursor = dbHelper.getReadableDatabase().query(
                "fruit_vegetable_list", // Table name
                null, // Select all columns
                "item_quantity > 0", // WHERE clause (only items with quantity > 0)
                null, // No arguments for WHERE
                null, // No GROUP BY
                null, // No HAVING
                null  // No ORDER BY
        );

        // Create the adapter with the cursor and set it to the GridView
        ItemsAdapter adapter = new ItemsAdapter(this, cursor, 0);
        gridView.setAdapter(adapter);
    }
}