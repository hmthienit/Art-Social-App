package com.example.grocerymanagement.views.ui.home.order;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.api.OrderManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.databinding.FragmentOrderAddBinding;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.order.model.Order;

import java.util.ArrayList;
import java.util.Date;


public class OrderAddFragment extends BaseFragment<FragmentOrderAddBinding> {


    @Override
    protected FragmentOrderAddBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentOrderAddBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
        binding.btnCancel.setOnClickListener(view -> popBackStack());
        binding.btnSave.setOnClickListener(view -> {
            String customerName = binding.editCustomerName.getText().toString();
            String customerNumberPhone = binding.edtCustomerPhone.getText().toString();
            boolean isEnabled = binding.orderEnableSwitch.isChecked();
            String createdAt = new Date().toString();
            String staffId = "123";
            ArrayList<Product> listProd = new ArrayList<>();
            Order order = new Order("", customerName, customerNumberPhone, listProd, staffId, createdAt, isEnabled);
            addOrder(order);
        });
    }

    private void addOrder(Order order) {
        OrderManager orderManager = new OrderManager();
        orderManager.addDocument(order, documentReference -> {
            order.setId(documentReference.getId());
            documentReference.set(order);
            showToast("The order " + order.getId() + " has been added successfully!");
            popBackStack();
        }, e -> showToast("Error while added the order " + order.getId()));
    }
}