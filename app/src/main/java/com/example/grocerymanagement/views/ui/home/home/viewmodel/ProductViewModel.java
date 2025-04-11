package com.example.grocerymanagement.views.ui.home.home.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerymanagement.views.ui.home.home.model.Product;

public class ProductViewModel extends ViewModel {
    MutableLiveData<Product> product = new MutableLiveData<>();

    public MutableLiveData<Product> getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product.setValue(product);
    }
}
