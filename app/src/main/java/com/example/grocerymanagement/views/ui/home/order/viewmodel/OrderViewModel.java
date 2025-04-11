package com.example.grocerymanagement.views.ui.home.order.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocerymanagement.views.ui.home.order.model.Order;

public class OrderViewModel extends ViewModel {
    MutableLiveData<Order> order = new MutableLiveData<>();
    MutableLiveData<Long> totalPrice = new MutableLiveData<>();

    public MutableLiveData<Order> getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order.setValue(order);
    }

    public MutableLiveData<Long> getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice.setValue(totalPrice);
    }
}
