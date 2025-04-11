package com.example.grocerymanagement.api;

import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.views.ui.home.home.model.Product;

public class ProductManager extends FirebaseHandler<Product> {
    public ProductManager() {
        super(AppConstant.PRODUCT);
    }
}
