package com.example.shoppingcollaborativeapp;

import java.io.Serializable;
import java.util.List;

public class ItemModel implements Serializable {

    private String name;
    private String icon;
    private String category;
    private List<String> suggestions;

    public ItemModel(String name, String icon, List<String> suggestions, String category) {
        this.name = name;
        this.icon = icon;
        this.category = category;
        this.suggestions = suggestions;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public String getCategory() {
        return category;
    }
}