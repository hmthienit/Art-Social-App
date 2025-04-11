package com.example.grocerymanagement.views.ui.home.sale.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.grocerymanagement.base.BaseAdapter;
import com.example.grocerymanagement.databinding.ItemSaleBinding;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;

public class SaleAdapter extends BaseAdapter<Sale, ItemSaleBinding> {

    private Context context;

    public SaleAdapter(Context context) {
        super(new SaleDiffCallback());
        this.context = context;

    }

    @Override
    public ItemSaleBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemSaleBinding.inflate(inflater);
    }

    @Override
    public void bind(@NonNull ItemSaleBinding binding, Sale item, int position) {

        binding.tvSaleName.setText(item.getSaleName());
        binding.tvStartDate.setText("Start date: " + item.getSaleStartDate());
        binding.tvEndDate.setText("End date: " + item.getSaleEndDate());
        binding.status.setText(item.getStatus() ? "Enabled" : "Disabled");

        binding.getRoot().setOnClickListener(v -> {

//            if (onItemClick != null) {
//                onItemClick.onItemClick(item);
//            }
        });
    }

    public static class SaleDiffCallback extends DiffUtil.ItemCallback<Sale> {
        @Override
        public boolean areItemsTheSame(@NonNull Sale oldItem, @NonNull Sale newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Sale oldItem, @NonNull Sale newItem) {
            return oldItem.equals(newItem);
        }
    }
}


