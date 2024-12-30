package com.example.myapplication;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_collab_project.R;

public class BakeryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery);

        initializeElements();
    }

    private void initializeElements() {
        applyHoverEffect(findViewById(R.id.btn_croissant), "Croissant");
        applyHoverEffect(findViewById(R.id.btn_baguette), "Baguette");
        applyHoverEffect(findViewById(R.id.btn_chocolate_bread), "Pain au chocolat");
        applyHoverEffect(findViewById(R.id.btn_brioche), "Brioche");
        applyHoverEffect(findViewById(R.id.btn_pie), "Tarte");
        applyHoverEffect(findViewById(R.id.btn_eclair), "Éclair");
    }

    private void applyHoverEffect(LinearLayout button, final String itemName) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundColor(Color.parseColor("#FFAB91")); // Couleur orange pâle au clic
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setBackgroundColor(Color.parseColor("#FFF3E0")); // Beige clair par défaut
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