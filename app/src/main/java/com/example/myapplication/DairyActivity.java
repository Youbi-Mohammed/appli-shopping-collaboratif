package com.example.myapplication;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.example.shopping_collab_project.R;

public class DairyActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);

        dbHelper = new MyDatabaseHelper(this);
        initializeElements();
    }

    private void initializeElements() {
        // Récupérer les boutons pour chaque produit laitier
        LinearLayout btnMilk = findViewById(R.id.btn_milk);
        LinearLayout btnCheese = findViewById(R.id.btn_cheese);
        LinearLayout btnButter = findViewById(R.id.btn_butter);
        LinearLayout btnYogurt = findViewById(R.id.btn_yogurt);
        LinearLayout btnCream = findViewById(R.id.btn_cream);
        LinearLayout btnIceCream = findViewById(R.id.btn_ice_cream);

        // Appliquer les effets de survol et de clic pour chaque bouton
        applyHoverEffect(btnMilk, "Lait");
        applyHoverEffect(btnCheese, "Fromage");
        applyHoverEffect(btnButter, "Beurre");
        applyHoverEffect(btnYogurt, "Yaourt");
        applyHoverEffect(btnCream, "Crème");
        applyHoverEffect(btnIceCream, "Glace");
    }

    private void applyHoverEffect(LinearLayout button, final String itemName) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundColor(Color.parseColor("#000080")); // Couleur sombre au clic
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setBackgroundColor(Color.parseColor("#E3F2FD")); // Couleur claire après le clic
                    break;
            }
            return false;
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuantityDialogActivity.class);
            intent.putExtra("item_name", itemName); // Passer le nom du produit à l'activité suivante
            startActivity(intent);
        });
    }
}