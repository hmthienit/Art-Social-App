package com.example.grocerymanagement.api;

import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.views.ui.home.category.model.Category;

public class CategoryManager extends FirebaseHandler<Category> {

    public CategoryManager() {
        super(AppConstant.CATEGORIES);
    }
}
