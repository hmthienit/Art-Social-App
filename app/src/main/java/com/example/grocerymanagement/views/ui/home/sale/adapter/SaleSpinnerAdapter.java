package com.example.grocerymanagement.views.ui.home.sale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseSpinnerAdapter;
import com.example.grocerymanagement.databinding.ItemBaseSpinnerBinding;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;

import java.util.List;

public class SaleSpinnerAdapter extends BaseSpinnerAdapter<Sale, ItemBaseSpinnerBinding> {
    public SaleSpinnerAdapter(Context context, List<Sale> categories) {
        super(context, R.layout.item_category_spinner, categories);
    }

    @Override
    protected ItemBaseSpinnerBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemBaseSpinnerBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ItemBaseSpinnerBinding binding, Sale item) {
        binding.tvName.setText(item.getSaleName());
    }

}
