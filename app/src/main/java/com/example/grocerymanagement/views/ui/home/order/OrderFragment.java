package com.example.grocerymanagement.views.ui.home.order;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.OrderManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentOrderBinding;
import com.example.grocerymanagement.utils.RecyclerViewUtils;
import com.example.grocerymanagement.views.ui.home.order.adapter.OrderAdapter;
import com.example.grocerymanagement.views.ui.home.order.model.Order;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment<FragmentOrderBinding> {

    OrderAdapter orderAdapter;
    List<Order> orders;
    OrderViewModel orderViewModel;

    @Override
    protected FragmentOrderBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentOrderBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        binding.layoutHeader.navHeaderName.setText(R.string.text_order_management);
        orderAdapter = new OrderAdapter(requireContext());
        RecyclerViewUtils.initAdapter(orderAdapter, binding.revOrder, 0, 1, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getListOrder();
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.tvAdd.setOnClickListener(view -> navigateTo(R.id.orderAddFragment));
        orderAdapter.setOnItemClickListener((view, position) -> {
            Order order = orders.get(position);
            orderViewModel.setOrder(order);
            navigateTo(R.id.orderDetailFragment);
        });
    }

    private void getListOrder() {
        showLoading();
        OrderManager orderManager = new OrderManager();
        orderManager.getAllDocuments(Order.class, queryDocumentSnapshots -> {
                    hideLoading();
                    orders = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Order order = document.toObject(Order.class);
                        orders.add(order);
                    }
                    orderAdapter.submitList(orders);
                    orderAdapter.notifyDataSetChanged();
                },
                e -> {
                    hideLoading();
                    showToast("Error while get list orders");
                }
        );
    }
}
