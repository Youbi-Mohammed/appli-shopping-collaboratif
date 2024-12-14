package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SearchView searchView;
    private ImageButton hamburgerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure this is your layout file

        // Initialize the drawer layout and navigation view
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        // Initialize the search view
        searchView = findViewById(R.id.search_view);
        hamburgerButton = findViewById(R.id.hamburger_button);

        // Handle search view input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // This is called when the user submits their search query
                Log.d("SearchView", "Query submitted: " + query);
                // Here you can perform a search or filter the UI based on the query
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // This is called as the user types into the search field
                Log.d("SearchView", "Query text changed: " + newText);
                // You can dynamically update your content here
                return true;
            }
        });

        // Handle hamburger button click to open the drawer
        hamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(navView); // Open the navigation drawer
                }
            }
        });
    }
}
