package com.example.myapplication;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuantityDialogActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity_dialog);

        dbHelper = new MyDatabaseHelper(this);

        String itemName = getIntent().getStringExtra("item_name");
        TextView tvItemName = findViewById(R.id.tv_item_name);
        tvItemName.setText("Quantité pour : " + itemName);

        EditText etQuantity = findViewById(R.id.et_quantity);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        Button btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            String quantityStr = etQuantity.getText().toString().trim();
            if (quantityStr.isEmpty() || radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Remplissez tous les champs.", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            String unit = radioButton.getText().toString();

            try {
                int quantity = Integer.parseInt(quantityStr);
                dbHelper.updateQuantity(itemName, quantity, unit); // Mise à jour de la base de données
                Toast.makeText(this, "Quantité mise à jour avec succès.", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Veuillez entrer une quantité valide.", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}