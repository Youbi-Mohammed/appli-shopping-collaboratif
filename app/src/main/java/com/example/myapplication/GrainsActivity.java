package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GrainsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grains);

        initializeElements();
    }

    private void initializeElements() {
        applyHoverEffect(findViewById(R.id.btn_rice), "Riz");
        applyHoverEffect(findViewById(R.id.btn_spaghetti), "Spaghettis");
        applyHoverEffect(findViewById(R.id.btn_cereals), "Céréales");
        applyHoverEffect(findViewById(R.id.btn_nouilles), "Nouilles");
        applyHoverEffect(findViewById(R.id.btn_flour), "Farine");
        applyHoverEffect(findViewById(R.id.btn_wheat), "Blé");
    }

    private void applyHoverEffect(LinearLayout button, final String itemName) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundColor(Color.parseColor("#FFEB3B")); // Jaune au clic
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setBackgroundColor(Color.parseColor("#E3F2FD")); // Bleu clair par défaut
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