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

public class MeatActivity extends AppCompatActivity {

    private GridView meatGridView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> meatItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat);

        meatGridView = findViewById(R.id.meatGridView);
        meatItems = new ArrayList<>();

        // Load fruit items (add more details here as per your app's needs)
        loadMeatItems();

        itemAdapter = new ItemAdapter(this, meatItems);
        meatGridView.setAdapter(itemAdapter);

        // Set item click listener for GridView
        meatGridView.setOnItemClickListener((parent, view, position, id) -> {
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

    private void loadMeatItems() {
        meatItems.add(new ItemModel("Poulet", "🍗", List.of("400g", "700g", "1kg"), "Viandes & Poissons"));
        meatItems.add(new ItemModel("Viande", "🍖", List.of("Bœuf", "Agneau", "1kg"), "Viandes & Poissons"));
        meatItems.add(new ItemModel("Crevettes", "🦐", List.of("400g", "600g", "1kg"), "Viandes & Poissons"));
        meatItems.add(new ItemModel("Poisson", "🐟", List.of("Sardines", "Saumon", "1kg"), "Viandes & Poissons"));
        meatItems.add(new ItemModel("Dinde", "🍗", List.of("300g", "500g", "800g"), "Viandes & Poissons"));
        meatItems.add(new ItemModel("Thon", "🦈", List.of("200g", "400g", "600g"), "Viandes & Poissons"));
        meatItems.add(new ItemModel("Crabe", "🦀", List.of("1", "2", "3"), "Viandes & Poissons"));
        meatItems.add(new ItemModel("Moules", "🐚", List.of("500g", "1kg", "2kg"), "Viandes & Poissons"));

        // Add more items as needed
    }

}