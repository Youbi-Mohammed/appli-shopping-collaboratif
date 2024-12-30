package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_collab_project.ProfilActivity;
import com.example.shopping_collab_project.R;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // Link to the main XML layout
        NavigationView nav=findViewById(R.id.nav_view);
        // Initialize the buttons and set click listeners
        Button fruitVegButton = findViewById(R.id.fruitVegButton);
        Button meatButton = findViewById(R.id.meatButton);
        Button dairyButton = findViewById(R.id.dairyButton);
        Button bakeryButton = findViewById(R.id.bakeryButton);
        Button grainsButton = findViewById(R.id.grainsButton);
        ImageButton hamburgerButton=findViewById(R.id.hamburger_button);
        hamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Fruits and Vegetables Activity
                startActivity(new Intent(MenuActivity.this, ProfilActivity.class));
            }
        });
        fruitVegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Fruits and Vegetables Activity
                startActivity(new Intent(MenuActivity.this, FruitsVegetablesActivity.class));
            }
        });

        meatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Meat Activity
                startActivity(new Intent(MenuActivity.this, MeatActivity.class));
            }
        });

        dairyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Dairy Products Activity
                startActivity(new Intent(MenuActivity.this, DairyActivity.class));
            }
        });

        bakeryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Bakery Activity
                startActivity(new Intent(MenuActivity.this, BakeryActivity.class));
            }
        });

        grainsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Grains Activity
                startActivity(new Intent(MenuActivity.this, GrainsActivity.class));
            }
        });
    }
}
