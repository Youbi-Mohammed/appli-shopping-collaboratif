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

public class BeveragesActivity extends AppCompatActivity {

    private GridView beveragesGridView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> beveragesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverages);

        beveragesGridView = findViewById(R.id.beveragesGridView);
        beveragesItems = new ArrayList<>();

        // Load fruit items (add more details here as per your app's needs)
        loadBeveragesItems();

        itemAdapter = new ItemAdapter(this, beveragesItems);
        beveragesGridView.setAdapter(itemAdapter);

        // Set item click listener for GridView
        beveragesGridView.setOnItemClickListener((parent, view, position, id) -> {
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

    private void loadBeveragesItems() {
        beveragesItems.add(new ItemModel("Café", "☕", List.of("1", "2", "4"), "Boissons"));
        beveragesItems.add(new ItemModel("Thé", "🍵", List.of("vert", "noir", "1"), "Boissons"));
        beveragesItems.add(new ItemModel("Jus", "🍹", List.of("d'orange", "de pomme", "tropical"), "Boissons"));
        beveragesItems.add(new ItemModel("Eau", "🏺", List.of("50cl", "1l", "1.5l"), "Boissons"));
        beveragesItems.add(new ItemModel("Limonade", "🍸", List.of("30cl", "50cl", "1l"), "Boissons"));

        // Add more items as needed
    }

}