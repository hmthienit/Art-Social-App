package com.example.grocerymanagement.views.ui.home.category;

import android.os.Bundle;
import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.ActivityCategoryDetailBinding;
import com.example.grocerymanagement.views.ui.home.category.model.Category;

public class CategoryDetailActivity extends BaseActivity<ActivityCategoryDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected ActivityCategoryDetailBinding getViewBinding() {
        return ActivityCategoryDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        Category category = (Category) getIntent().getSerializableExtra(AppConstant.CATEGORIES);
        categoryViewModel.setCategory(category);
    }

    @Override
    protected void initAction() {

    }
}