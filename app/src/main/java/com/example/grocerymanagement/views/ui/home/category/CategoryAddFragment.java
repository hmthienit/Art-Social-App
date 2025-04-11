package com.example.grocerymanagement.views.ui.home.category;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentCategoryAddBinding;
import com.example.grocerymanagement.views.ui.home.category.model.Category;

import java.util.Date;

public class CategoryAddFragment extends BaseFragment<FragmentCategoryAddBinding> {


    @Override
    protected FragmentCategoryAddBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCategoryAddBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAction() {
        binding.btnSave.setOnClickListener(view -> addCategory(new Category("", binding.editCategoryName.getText().toString(), binding.categoryEnableSwitch.isChecked(), new Date().toString())));
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
    }

    public void addCategory(Category category) {
        db.collection(AppConstant.CATEGORIES).add(category).addOnSuccessListener(documentReference -> {
            String documentId = documentReference.getId();
            category.setId(documentId);
            documentReference.set(category);
            showToast("Category added with name: " + category.getCategoryName());
            popBackStack();
        }).addOnFailureListener(e -> {
            showToast("Error adding category: " + e.getMessage());
        });
    }
}