package com.example.grocerymanagement.views.ui.home.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;

import com.example.grocerymanagement.api.OrderManager;
import com.example.grocerymanagement.base.BaseAdapter;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.constants.ProductType;
import com.example.grocerymanagement.databinding.ItemHomeProductBinding;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.order.model.Order;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductHomeAdapter extends BaseAdapter<Product, ItemHomeProductBinding> {
    //    private OnItemClickListener onItemClickListener;
    private final Context context;
    private final ProductType productType;
    private final OrderViewModel orderViewModel;

    public ProductHomeAdapter(Context context, ProductType productType, OrderViewModel orderViewModel) {
        super(new ProductHomeDiffCallback());
        this.context = context;
        this.productType = productType;
        this.orderViewModel = orderViewModel;
    }

    @Override
    public ItemHomeProductBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemHomeProductBinding.inflate(inflater);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bind(@NonNull ItemHomeProductBinding binding, Product item, int position) {

        if (productType == ProductType.PRODUCT_HOME) {
            binding.icClose.setVisibility(View.GONE);
        } else {
            binding.icClose.setVisibility(View.VISIBLE);
        }

        ImageUtils.getInstance(context).setImage(binding.productImage, AppConstant.BASE_URL + item.getImage());
        binding.productTitle.setText(item.getName());
        binding.productPrice.setText(item.getPrice() + " VND ");
        binding.productQuantity.setText("Quantity: " + item.getQuantity());
        binding.progressbar.setVisibility(View.GONE);
        if (item.getSale().getSaleValue() != 0) {
            binding.productPrice.setPaintFlags(binding.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.productPriceSale.setText((item.getPrice() - ((item.getPrice() * item.getSale().getSaleValue()) / 100)) + " VND");
            binding.productPriceSale.setVisibility(View.VISIBLE);
        } else {
            binding.productPriceSale.setVisibility(View.INVISIBLE);
            binding.productPrice.setPaintFlags(binding.productPrice.getPaintFlags() | Paint.LINEAR_TEXT_FLAG);
        }

        binding.icClose.setOnClickListener(view -> {
            OrderManager orderManager = new OrderManager();
            orderManager.removeItemFromListProdInOrder(orderViewModel.getOrder().getValue().getId(), item.getId(), orderViewModel);
//            getListOrder();
            removeItem(position);
        });

    }

    public static class ProductHomeDiffCallback extends DiffUtil.ItemCallback<Product> {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    }

    private void getListOrder() {
        OrderManager orderManager = new OrderManager();
        orderManager.getDocument(Objects.requireNonNull(orderViewModel.getOrder().getValue()).getId(), Order.class, queryDocumentSnapshots -> {
                    Order order = queryDocumentSnapshots.toObject(Order.class);
                    orderViewModel.setOrder(order);
                    long totalPrice = 0;
                    for (Product prodItem : order.getListProd()) {
                        totalPrice += prodItem.getSale().getSaleValue() == 0 ? (long) prodItem.getPrice() * prodItem.getQuantity() : (prodItem.getPrice() - ((prodItem.getPrice() * (prodItem.getSale().getSaleValue())) / 100)) * prodItem.getQuantity();
                    }
                    orderViewModel.setTotalPrice(totalPrice);
                },
                e -> {
                }
        );
    }
}

