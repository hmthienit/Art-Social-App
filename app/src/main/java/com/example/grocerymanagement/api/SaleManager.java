package com.example.grocerymanagement.api;

import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;

public class SaleManager extends FirebaseHandler<Sale> {
    public SaleManager() {
        super(AppConstant.SALES);
    }
}
