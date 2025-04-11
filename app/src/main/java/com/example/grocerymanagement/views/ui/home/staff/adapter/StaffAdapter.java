package com.example.grocerymanagement.views.ui.home.staff.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.grocerymanagement.base.BaseAdapter;
import com.example.grocerymanagement.databinding.ItemStaffBinding;
import com.example.grocerymanagement.views.ui.home.staff.model.Staff;

public class StaffAdapter extends BaseAdapter<Staff, ItemStaffBinding> {

    public StaffAdapter() {
        super(new StaffDiffCallback());
    }

    @Override
    public ItemStaffBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemStaffBinding.inflate(inflater);
    }

    @Override
    public void bind(@NonNull ItemStaffBinding binding, Staff item, int position) {
        binding.progressbar.setVisibility(ViewGroup.GONE);
        binding.tvPosition.setText(item.getStaffPosition());
        binding.tvStaffName.setText(item.getStaffName());
        binding.staffId.setText(item.getStaffCodeNumber());
        binding.tvStatus.setText(item.isEnable() ? "Enabled" : "Disabled");

        binding.getRoot().setOnClickListener(v -> {
        });
    }

    public static class StaffDiffCallback extends DiffUtil.ItemCallback<Staff> {
        @Override
        public boolean areItemsTheSame(@NonNull Staff oldItem, @NonNull Staff newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Staff oldItem, @NonNull Staff newItem) {
            return oldItem.equals(newItem);
        }
    }
}
