package com.example.myapplication;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat);

        initializeMeatButtons();
    }

    private void initializeMeatButtons() {
        LinearLayout btnChicken = findViewById(R.id.btn_chicken);
        LinearLayout btnBeef = findViewById(R.id.btn_beef);
        LinearLayout btnPork = findViewById(R.id.btn_saucisse);
        LinearLayout btnFish = findViewById(R.id.btn_fish);
        LinearLayout btnLamb = findViewById(R.id.btn_lamb);
        LinearLayout btnDuck = findViewById(R.id.btn_dinde);

        applyHoverEffect(btnChicken, "Poulet");
        applyHoverEffect(btnBeef, "Boeuf");
        applyHoverEffect(btnPork, "Porc");
        applyHoverEffect(btnFish, "Poisson");
        applyHoverEffect(btnLamb, "Agneau");
        applyHoverEffect(btnDuck, "Canard");
    }

    private void applyHoverEffect(LinearLayout button, final String itemName) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundColor(Color.parseColor("#D32F2F")); // Rouge foncé pour l'effet de clic
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setBackgroundColor(Color.parseColor("#FFCDD2")); // Rose clair pour le fond par défaut
                    break;
            }
            return false;
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuantityDialogActivity.class);
            intent.putExtra("item_name", itemName);
            startActivity(intent);
        });
    }

}