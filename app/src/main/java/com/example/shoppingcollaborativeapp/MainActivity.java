package com.example.shoppingcollaborativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView categoryLisView;
    private Map<String, List<ItemModel>> categoryItems = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryLisView = findViewById(R.id.categoryListView);

        List<String> categories = new ArrayList<>();
        categories.add("Fruits & Légumes");
        categories.add("Viandes & Poissons");
        categories.add("Produits laitiers & œufs");
        categories.add("Boissons");
        categories.add("Céréales & pâtes");

        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        categoryLisView.setAdapter(adapter);

        categoryLisView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = categories.get(position);

            Intent intent;
            switch (selectedCategory) {
                case "Fruits & Légumes":
                    intent = new Intent(MainActivity.this, FruitVegetableActivity.class);
                    break;
                case "Viandes & Poissons":
                    intent = new Intent(MainActivity.this, MeatActivity.class);
                    break;
                case "Produits laitiers & œufs":
                    intent = new Intent(MainActivity.this, DairyEggsActivity.class);
                    break;
                case "Boissons":
                    intent = new Intent(MainActivity.this, BeveragesActivity.class);
                    break;
                case "Céréales & pâtes":
                    intent = new Intent(MainActivity.this, GrainsActivity.class);
                    break;
                default:
                    return;
            }
            startActivity(intent);
        });
    }
}