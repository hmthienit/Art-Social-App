package com.example.grocerymanagement.views.ui.home.category.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.grocerymanagement.base.BaseViewModel;
import com.example.grocerymanagement.views.ui.home.category.model.Category;

public class CategoryViewModel extends BaseViewModel {

    MutableLiveData<Category> category = new MutableLiveData<>();

    public MutableLiveData<Category> getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category.setValue(category);
    }
}
