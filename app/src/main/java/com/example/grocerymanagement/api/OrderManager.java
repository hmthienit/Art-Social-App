package com.example.grocerymanagement.api;

import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.views.ui.home.order.model.Order;

public class OrderManager extends FirebaseHandler<Order>{
    public OrderManager() {
        super(AppConstant.ORDERS);
    }
}
