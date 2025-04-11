package com.example.grocerymanagement.views.ui.home.staff.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseSpinnerAdapter;
import com.example.grocerymanagement.databinding.ItemBaseSpinnerBinding;

import java.util.List;

public class PositionSpinnerAdapter extends BaseSpinnerAdapter<String, ItemBaseSpinnerBinding> {
    public PositionSpinnerAdapter(Context context, List<String> categories) {
        super(context, R.layout.item_category_spinner, categories);
    }

    @Override
    protected ItemBaseSpinnerBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemBaseSpinnerBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ItemBaseSpinnerBinding binding, String item) {
        binding.tvName.setText(item);
    }

}