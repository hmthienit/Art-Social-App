package com.example.grocerymanagement.views.ui.home.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.grocerymanagement.base.BaseAdapter;
import com.example.grocerymanagement.databinding.ItemClientBinding;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.user.model.Client;

public class ClientAdapter extends BaseAdapter<Client, ItemClientBinding> {

    private Context context;

    public ClientAdapter(Context context) {
        super(new ClientDiffCallback());
        this.context = context;

    }

    @Override
    public ItemClientBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemClientBinding.inflate(inflater);
    }

    @Override
    public void bind(@NonNull ItemClientBinding binding, Client item, int position) {
        binding.progressbar.setVisibility(ViewGroup.GONE);
        binding.tvStaffName.setText(item.getCustomerName());
        binding.phoneNumber.setText(item.getPhoneNumber());
        binding.tvStatus.setText(item.isEnabled() ? "Enabled" : "Disabled");
        binding.getRoot().setOnClickListener(v -> {
        });
    }

    public static class ClientDiffCallback extends DiffUtil.ItemCallback<Client> {
        @Override
        public boolean areItemsTheSame(@NonNull Client oldItem, @NonNull Client newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Client oldItem, @NonNull Client newItem) {
            return oldItem.equals(newItem);
        }
    }
}



