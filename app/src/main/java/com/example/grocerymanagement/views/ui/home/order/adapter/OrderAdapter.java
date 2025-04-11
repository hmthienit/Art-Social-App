package com.example.grocerymanagement.views.ui.home.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.grocerymanagement.base.BaseAdapter;
import com.example.grocerymanagement.databinding.ItemOrderBinding;
import com.example.grocerymanagement.views.ui.home.order.model.Order;

public class OrderAdapter extends BaseAdapter<Order, ItemOrderBinding> {

    private Context context;

    public OrderAdapter(Context context) {
        super(new OrderAdapter.OrderDiffCallback());
        this.context = context;

    }

    @Override
    public ItemOrderBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemOrderBinding.inflate(inflater);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bind(@NonNull ItemOrderBinding binding, Order item, int position) {

        binding.tvCustomerName.setText(item.getCustomerName());
        binding.tvOrderDate.setText("Order date: " + item.getCreatedAt());
        binding.tvOrderName.setText("Order ID: " + item.getId());
        binding.tvStatus.setText(item.isEnable() ? "Enabled" : "Disabled");

        binding.getRoot().setOnClickListener(v -> {

        });
    }

    public static class OrderDiffCallback extends DiffUtil.ItemCallback<Order> {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.equals(newItem);
        }
    }
}
