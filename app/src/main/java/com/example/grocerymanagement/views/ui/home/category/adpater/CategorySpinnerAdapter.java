package com.example.grocerymanagement.views.ui.home.category.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.views.ui.home.category.model.Category;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    public CategorySpinnerAdapter(Context context, List<Category> categories) {
        super(context, R.layout.item_category_spinner, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category_spinner, parent, false);
        }
        Category category = getItem(position);
        TextView name = convertView.findViewById(R.id.categoryName);
        name.setText(category.getCategoryName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category_spinner, parent, false);
        }
        Category category = getItem(position);
        TextView name = convertView.findViewById(R.id.categoryName);
        name.setText(category.getCategoryName());
        return convertView;
    }
}
