package com.example.grocerymanagement.views.ui.home.order;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.api.OrderManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentOrderEditBinding;
import com.example.grocerymanagement.views.ui.home.order.model.Order;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class OrderEditFragment extends BaseFragment<FragmentOrderEditBinding> {

    private OrderViewModel orderViewModel;


    @Override
    protected FragmentOrderEditBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentOrderEditBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        orderViewModel.getOrder().observe(getViewLifecycleOwner(), new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                binding.editCustomerName.setText(order.getCustomerName());
                binding.editPhoneNumber.setText(order.getPhoneNumber());
                binding.orderEnableSwitch.setChecked(order.isEnable());
            }
        });

    }

    @Override
    protected void initAction() {
        binding.btnSave.setOnClickListener(view -> {
            OrderManager orderManager = new OrderManager();
            Map<String, Object> orderUpdate = new HashMap<>();
            orderUpdate.put(AppConstant.ORDER_CUSTOMER_NAME, binding.editCustomerName.getText().toString());
            orderUpdate.put(AppConstant.ORDER_CUSTOMER_NUMBER_PHONE, binding.editPhoneNumber.getText().toString());
            orderUpdate.put(AppConstant.ORDER_STATUS, binding.orderEnableSwitch.isChecked());
            orderManager.updateDocument(Objects.requireNonNull(orderViewModel.getOrder().getValue()).getId(), orderUpdate, aVoid -> {
                orderManager.getDocument(orderViewModel.getOrder().getValue().getId(), Order.class, documentSnapshot -> {
                    showToast("The order has been updated!");
                    orderViewModel.setOrder(documentSnapshot.toObject(Order.class));
                    popBackStack();
                }, e -> {
                });
            }, e -> {
                showToast("Error when updated the order");
            });
        });

        binding.btnCancel.setOnClickListener(view -> popBackStack());
    }
}