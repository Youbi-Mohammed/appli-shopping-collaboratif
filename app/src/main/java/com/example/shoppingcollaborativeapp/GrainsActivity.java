package com.example.shoppingcollaborativeapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class GrainsActivity extends AppCompatActivity {

    private GridView grainsGridView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> grainsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grains);

        grainsGridView = findViewById(R.id.grainsGridView);
        grainsItems = new ArrayList<>();

        // Load fruit items (add more details here as per your app's needs)
        loadGrainsItems();

        itemAdapter = new ItemAdapter(this, grainsItems);
        grainsGridView.setAdapter(itemAdapter);

        // Set item click listener for GridView
        grainsGridView.setOnItemClickListener((parent, view, position, id) -> {
            ItemModel selectedItem = (ItemModel) itemAdapter.getItem(position);

            // Create BottomSheetDialog and inflate layout
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            @SuppressLint("InflateParams")
            View sheetView = getLayoutInflater().inflate(R.layout.quantity_description, null);

            // Dynamically set the name of the selected item from ItemModel
            TextView title = sheetView.findViewById(R.id.itemName);
            TextView titleDetail = sheetView.findViewById(R.id.itemName2);
            EditText descriptionEditText = sheetView.findViewById(R.id.description_edit_text);
            CheckBox urgentCheckBox = sheetView.findViewById(R.id.urgent_checkbox);

            if (title != null) {
                title.setText(selectedItem.getName()); // Dynamically set the name of the item
            }

            if (titleDetail != null) {
                titleDetail.setText(selectedItem.getName()); // Dynamically set the name of the item
            }

            // Populate buttons with suggestions
            LinearLayout buttonContainer = sheetView.findViewById(R.id.buttonContainer);
            if (buttonContainer != null) {
                buttonContainer.removeAllViews();
                for (String suggestion : selectedItem.getSuggestions()) {
                    Button suggestionButton = new Button(this);
                    suggestionButton.setText(suggestion);
                    suggestionButton.setId(View.generateViewId()); // Dynamically generate unique ID
                    suggestionButton.setOnClickListener(v -> {
                        if (descriptionEditText != null) {
                            String currentText = descriptionEditText.getText().toString();

                            // Add a comma and the new suggestion if there's existing text
                            if (!currentText.isEmpty()) {
                                descriptionEditText.setText(currentText + ", " + suggestion);
                            } else {
                                // Add suggestion if the description is empty
                                descriptionEditText.setText(suggestion);
                            }
                        }
                    });
                    buttonContainer.addView(suggestionButton);
                }
            }

            // Show the BottomSheet
            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

            Button saveButton = sheetView.findViewById(R.id.done_button);
            saveButton.setOnClickListener(v -> {
                String description = descriptionEditText.getText().toString();
                boolean isUrgent = urgentCheckBox.isChecked();

                // Insert the item into the database
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                dbHelper.insertItem(selectedItem.getName(), selectedItem.getIcon(), description, isUrgent);

                // Close the BottomSheet after saving
                bottomSheetDialog.dismiss();
            });
        });
    }

    private void loadGrainsItems() {
        grainsItems.add(new ItemModel("Riz", "🍚", List.of("blanc", "basmati", "complet"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel("Baguette", "🥖", List.of("complet", "1", "2"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel("Pain", "🍞", List.of("complet", "blanc", "Bagel"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel("Flocons d'avoine", "🌾", List.of("200g", "500g", "1kg"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel(" Croissant ", "🥐", List.of("au chocolat", "1", "2"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel("spaghetti", "🍝", List.of("complet", "250g", "500g"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel("Nouilles", "🍜", List.of("ramen", "1", "2"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel("Donut", "🍩", List.of("au chocolat", "1", "2"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel("Cookie", "🍪", List.of("2", "4", "6"), "Céréales & pâtes"));
        grainsItems.add(new ItemModel("Gâteau", "🍰", List.of("aux fruits", "1", "2"), "Céréales & pâtes"));


        // Add more items as needed
    }
}