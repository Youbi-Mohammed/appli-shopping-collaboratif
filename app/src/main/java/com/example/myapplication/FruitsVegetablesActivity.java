package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.example.shopping_collab_project.R;
import androidx.appcompat.app.AppCompatActivity;

public class FruitsVegetablesActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits_vegetables);

        dbHelper = new MyDatabaseHelper(this);
        initializeElements();
    }

    private void initializeElements() {
        LinearLayout btnApple = findViewById(R.id.btn_apple);
        LinearLayout btnBanana = findViewById(R.id.btn_banana);
        LinearLayout btnCarrot = findViewById(R.id.btn_carrot);
        LinearLayout btnPotato = findViewById(R.id.btn_potato);
        LinearLayout btnTomato = findViewById(R.id.btn_tomato);
        LinearLayout btnOnion = findViewById(R.id.btn_onion);

        applyHoverEffect(btnApple, "Apple");
        applyHoverEffect(btnBanana, "Banana");
        applyHoverEffect(btnCarrot, "Carrot");
        applyHoverEffect(btnPotato, "Potato");
        applyHoverEffect(btnTomato, "Tomato");
        applyHoverEffect(btnOnion, "Onion");
    }

    private void applyHoverEffect(LinearLayout button, final String itemName) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundColor(Color.parseColor("#000080"));
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setBackgroundColor(Color.parseColor("#E3F2FD"));
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