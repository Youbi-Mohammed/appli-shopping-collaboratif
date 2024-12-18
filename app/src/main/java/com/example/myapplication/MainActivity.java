package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Link to the main XML layout

        // Initialize the buttons and set click listeners
        Button fruitVegButton = findViewById(R.id.fruitVegButton);
        Button meatButton = findViewById(R.id.meatButton);
        Button dairyButton = findViewById(R.id.dairyButton);
        Button bakeryButton = findViewById(R.id.bakeryButton);
        Button grainsButton = findViewById(R.id.grainsButton);

        fruitVegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Fruits and Vegetables Activity
                startActivity(new Intent(MainActivity.this, FruitsVegetablesActivity.class));
            }
        });

        meatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Meat Activity
                startActivity(new Intent(MainActivity.this, MeatActivity.class));
            }
        });

        dairyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Dairy Products Activity
                startActivity(new Intent(MainActivity.this, DairyActivity.class));
            }
        });

        bakeryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Bakery Activity
                startActivity(new Intent(MainActivity.this, BakeryActivity.class));
            }
        });

        grainsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Grains Activity
                startActivity(new Intent(MainActivity.this, GrainsActivity.class));
            }
        });
    }
}
