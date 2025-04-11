package com.example.grocerymanagement.views.ui.home.sale.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerymanagement.views.ui.home.sale.model.Sale;

public class SaleViewModel extends ViewModel {
    MutableLiveData<Sale> sale = new MutableLiveData<>();

    public MutableLiveData<Sale> getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale.setValue(sale);
    }
}
