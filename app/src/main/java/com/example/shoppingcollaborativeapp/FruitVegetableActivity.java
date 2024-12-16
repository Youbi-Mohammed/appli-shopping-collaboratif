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

public class FruitVegetableActivity extends AppCompatActivity {

    private GridView fruitVegetableGridView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> fruitVegetableItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_vegetable);

        fruitVegetableGridView = findViewById(R.id.fruitVegetableGridView);
        fruitVegetableItems = new ArrayList<>();

        // Load fruit items (add more details here as per your app's needs)
        loadFruitVegetableItems();

        itemAdapter = new ItemAdapter(this, fruitVegetableItems);
        fruitVegetableGridView.setAdapter(itemAdapter);

        // Set item click listener for GridView
        fruitVegetableGridView.setOnItemClickListener((parent, view, position, id) -> {
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

    private void loadFruitVegetableItems() {
        fruitVegetableItems.add(new ItemModel("Pomme", "🍎", List.of("1kg", "verte", "rouge"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("Banane", "🍌", List.of("1", "4", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("Orange", "🍊", List.of("Tangerine", "Orange Navel", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("Raisin", "🍇", List.of("noir", "vert", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("Pêche", "🍑", List.of("4", "1kg", "2kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("Fraise", "🍓", List.of("500g", "1kg", "1.5kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("citron", "🍋", List.of("1", "2", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("avocat", "🥑", List.of("1", "2", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("kiwi", "🥝", List.of("2", "4", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("poire", "🍐", List.of("2", "1kg", "1.5kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("pastèque", "🍉", List.of("1", "petit", "grand"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("melon", "🍈", List.of("1", "petit", "grand"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("cerises", "🍒", List.of("250g", "500g", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("ananas", "🍍", List.of("1", "boite", "jus"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("concombre", "🥒", List.of("1", "2", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("tomate", "🍅", List.of("2", "4", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("aubergine", "🍆", List.of("1", "2", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("carotte", "🥕", List.of("500g", "1kg", "2kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("patate douce", "🍠", List.of("2", "4", "1kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("pomme de terre", "🥔", List.of("1kg", "2kg", "3kg"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("piment fort", "🌶️", List.of("rouge", "verte", "5"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("maïs", "🌽", List.of("1", "2", "4"), "Fruits & Légumes"));
        fruitVegetableItems.add(new ItemModel("champignon", "🍄‍", List.of("200g", "300g", "400g"), "Fruits & Légumes"));
        // Add more items as needed
    }

}