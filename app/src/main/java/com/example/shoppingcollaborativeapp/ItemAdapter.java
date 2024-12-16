package com.example.shoppingcollaborativeapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<ItemModel> items;

    public ItemAdapter(Context context, List<ItemModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.textView);
        TextView itemIcon = convertView.findViewById(R.id.emoji);

        ItemModel item = items.get(position);
        itemName.setText(item.getName());
        itemIcon.setText(item.getIcon());

        String uniqueId = "item_" + item.getName().replace("\\s", "_");
        convertView.setId(View.generateViewId());

        Log.d("ItemAdapter", "View ID for " + item.getName() + ": " + convertView.getId());

        return convertView;
    }
}