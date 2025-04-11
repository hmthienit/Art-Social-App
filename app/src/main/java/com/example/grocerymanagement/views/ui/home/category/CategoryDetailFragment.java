package com.example.grocerymanagement.views.ui.home.category;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.CategoryManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.databinding.FragmentCategoryDetailBinding;
import com.example.grocerymanagement.helper.DialogHelper;
import com.example.grocerymanagement.views.ui.home.category.model.Category;
import com.example.grocerymanagement.views.ui.home.category.viewmodel.CategoryViewModel;

public class CategoryDetailFragment extends BaseFragment<FragmentCategoryDetailBinding> {

    private CategoryViewModel categoryViewModel;

    @Override
    protected FragmentCategoryDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCategoryDetailBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews() {
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        Category category = categoryViewModel.getCategory().getValue();
        if (category != null) {
            binding.layoutHeader.navHeaderName.setText(category.getCategoryName());
            binding.categoryName.setText("Category Name: " + category.getCategoryName());
            binding.categoryCreatedAt.setText("Created Date: " + category.getCreatedAt());
            binding.categoryId.setText("Category ID: " + category.getId());
            binding.categoryStatus.setText(category.getIsEnable() ? "Category Status: " + getString(R.string.text_enabled) : "Category Status: " + getString(R.string.text_disabled));
        }
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(view -> closeActivity());
        binding.btnEdit.setOnClickListener(view -> navigateTo(R.id.categoryEditFragment));
        binding.btnDelete.setOnClickListener(view -> {
            Category category = categoryViewModel.getCategory().getValue();
            assert category != null;
            DialogHelper.showConfirmationDialog(requireContext(),
                    "Delete " + category.getCategoryName(),
                    "Would you like to delete the " + category.getCategoryName(),
                    "Delete",
                    (dialogInterface, i) -> deleteCategory(category),
                    "Cancel",
                    (dialogInterface, i) -> dialogInterface.dismiss());
        });
    }

    private void deleteCategory(Category category) {
        CategoryManager categoryManager = new CategoryManager();
        categoryManager.deleteDocument(category.getId(), unused ->
        {
            showToast("The category " + category.getCategoryName() + " has been delete succesfully!");
            requireActivity().finish();
        }, e -> showToast("Error while delete the category " + category.getCategoryName()));
    }
}