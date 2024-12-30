package com.example.myapplication;

import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shopping_collab_project.R;
public class lienbutton4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Link the XML layout to this activity

        // Find the button by its ID
        Button buttonToPage = findViewById(R.id.bakeryButton);

        // Set an OnClickListener for the button
        buttonToPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the next activity
                Intent intent = new Intent(lienbutton4.this, DairyActivity.class);
                startActivity(intent);
            }
        });
    }
}

