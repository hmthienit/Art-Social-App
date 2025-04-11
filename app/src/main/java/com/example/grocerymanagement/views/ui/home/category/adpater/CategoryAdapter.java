package com.example.grocerymanagement.views.ui.home.category.adpater;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import com.example.grocerymanagement.base.BaseAdapter;
import com.example.grocerymanagement.databinding.ItemCategoryBinding;
import com.example.grocerymanagement.views.ui.home.category.model.Category;

public class CategoryAdapter extends BaseAdapter<Category, ItemCategoryBinding> {

    public CategoryAdapter() {
        super(new CategoryDiffCallback());
    }

    @Override
    public ItemCategoryBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemCategoryBinding.inflate(inflater);
    }

    @Override
    public void bind(@NonNull ItemCategoryBinding binding, Category item, int position) {
//        binding.setDataSortOption(item);
//        binding.executePendingBindings();
        binding.tvCategoryId.setText((position + 1) + "");
        binding.tvCategoryName.setText(item.getCategoryName());
        binding.tvStatus.setText(item.getIsEnable() ? "Enabled" : "Disabled");

        binding.getRoot().setOnClickListener(v -> {

//            if (onItemClick != null) {
//                onItemClick.onItemClick(item);
//            }
        });
    }

    public static class CategoryDiffCallback extends DiffUtil.ItemCallback<Category> {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.equals(newItem);
        }
    }
}

