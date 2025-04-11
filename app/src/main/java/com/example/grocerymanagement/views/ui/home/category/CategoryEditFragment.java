package com.example.grocerymanagement.views.ui.home.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentCategoryEditBinding;
import com.example.grocerymanagement.views.ui.home.category.model.Category;
import com.example.grocerymanagement.views.ui.home.category.viewmodel.CategoryViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CategoryEditFragment extends BaseFragment<FragmentCategoryEditBinding> {

    private CategoryViewModel categoryViewModel;

    @Override
    protected FragmentCategoryEditBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCategoryEditBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        Category category = categoryViewModel.getCategory().getValue();

        if (category != null) {
            binding.editCategoryName.setText(category.getCategoryName());
            binding.categoryEnableSwitch.setChecked(category.getIsEnable());
        }
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
        binding.btnSave.setOnClickListener(view -> {
            Map<String, Object> category = new HashMap<>();
            category.put(AppConstant.CATE_NAME, binding.editCategoryName.getText().toString());
            category.put(AppConstant.CATE_IS_ENABLED, binding.categoryEnableSwitch.isChecked());
            editCategory(Objects.requireNonNull(categoryViewModel.getCategory().getValue()).getId(), category);
        });
    }

    public void editCategory(String categoryID, Map<String, Object> category) {

        db.collection(AppConstant.CATEGORIES).document(String.valueOf(categoryID)).update(category).addOnSuccessListener(aVoid -> {
            showToast("Category updated successfully");
            backToPrevActivity();
        }).addOnFailureListener(e -> {
            showToast("Failed to update category");
        });

    }
}