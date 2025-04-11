package com.example.grocerymanagement.views.ui.home.order;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.OrderManager;
import com.example.grocerymanagement.base.BaseAdapter;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.constants.ProductType;
import com.example.grocerymanagement.databinding.FragmentOrderDetailBinding;
import com.example.grocerymanagement.helper.DialogHelper;
import com.example.grocerymanagement.utils.RecyclerViewUtils;
import com.example.grocerymanagement.views.ui.home.home.adapter.ProductHomeAdapter;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.order.model.Order;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class OrderDetailFragment extends BaseFragment<FragmentOrderDetailBinding> {
    OrderViewModel orderViewModel;
    ProductHomeAdapter productHomeAdapter;
    ArrayList<Order> orders;

    @Override
    protected FragmentOrderDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentOrderDetailBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews() {
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        orderViewModel.getOrder().observe(getViewLifecycleOwner(), new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                binding.layoutHeader.navHeaderName.setText(order.getCustomerName());
                binding.orderCustomerName.setText("Customer name: " + order.getCustomerName());
                binding.orderCreatedAt.setText("Order date: " + order.getCreatedAt());
                binding.orderPhoneNumber.setText("Customer number: " + order.getPhoneNumber());

            }
        });

        orderViewModel.getTotalPrice().observe(getViewLifecycleOwner(), aLong -> binding.tvTotalPrice.setText("Total Price: " + orderViewModel.getTotalPrice().getValue() + "VND"));

    }

    @Override
    public void onResume() {
        super.onResume();
        getListOrder();

    }

    public void getListProductOrdered() {
        productHomeAdapter = new ProductHomeAdapter(requireContext(), ProductType.PRODUCT_ORDER, orderViewModel);
        RecyclerViewUtils.initAdapter(productHomeAdapter, binding.productRecyclerView, 0, 2, false);
        productHomeAdapter.submitList(Objects.requireNonNull(orderViewModel.getOrder().getValue()).getListProd());
        productHomeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initAction() {

        binding.btnAddProduct.setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppConstant.ORDERS, orderViewModel.getOrder().getValue());
                    startActivityWithData(OrderScannerActivity.class, bundle);
                }
        );
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
        binding.btnEditOrder.setOnClickListener(view -> navigateTo(R.id.orderEditFragment));
        binding.btnDeleteOrder.setOnClickListener(view -> {
            orderViewModel.getOrder().observe(getViewLifecycleOwner(), new Observer<Order>() {
                @Override
                public void onChanged(Order order) {
                    DialogHelper.showConfirmationDialog(requireContext(), "Delete " + order.getId(), "Would you like to detele the order " + order.getId(), "Delete", (dialogInterface, i) -> {
                        OrderManager orderManager = new OrderManager();
                        orderManager.deleteDocument(order.getId(), unused -> {
                            showToast("Delete the order " + order.getId() + " suceed!");
                            popBackStack();
                        }, e -> {
                            showToast("Error when delete the order!");
                        });
                    }, "Cancel", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
                }
            });

        });
    }

    @SuppressLint("SetTextI18n")
    private void getListOrder() {
        OrderManager orderManager = new OrderManager();
        orderManager.getDocument(Objects.requireNonNull(orderViewModel.getOrder().getValue()).getId(), Order.class, queryDocumentSnapshots -> {

                    Order order = queryDocumentSnapshots.toObject(Order.class);
                    orderViewModel.setOrder(order);
                    orderViewModel.getOrder().observe(getViewLifecycleOwner(), new Observer<Order>() {
                        @Override
                        public void onChanged(Order order) {
                            long totalPrice = 0;
                            if (order.getListProd() != null) {
                                for (Product prodItem : order.getListProd()) {
                                    totalPrice += prodItem.getSale().getSaleValue() == 0 ? (long) prodItem.getPrice() * prodItem.getQuantity() : (prodItem.getPrice() - ((prodItem.getPrice() * (prodItem.getSale().getSaleValue())) / 100)) * prodItem.getQuantity();
                                }
                            }

                            orderViewModel.setTotalPrice(totalPrice);

                            binding.tvTotalPrice.setText("Total Price: " + orderViewModel.getTotalPrice().getValue() + "VND");
                        }
                    });
                    getListProductOrdered();
                },
                e -> showToast("Error while get list orders")
        );
    }
}