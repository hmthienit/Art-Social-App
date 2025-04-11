package com.example.grocerymanagement.views.ui.home.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.constants.ProductType;
import com.example.grocerymanagement.databinding.FragmentHomeBinding;
import com.example.grocerymanagement.utils.RecyclerViewUtils;
import com.example.grocerymanagement.views.ui.home.home.adapter.ProductHomeAdapter;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    HomeViewModel homeViewModel;
    OrderViewModel orderViewModel;
    private ProductHomeAdapter productHomeAdapter;
    List<Product> listProd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected FragmentHomeBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        binding.layoutHeader.navHeaderName.setText(R.string.text_product_management);

        productHomeAdapter = new ProductHomeAdapter(requireContext(), ProductType.PRODUCT_HOME, orderViewModel);
        RecyclerViewUtils.initAdapter(productHomeAdapter, binding.revProductHome, 0, 2, false);

    }

    @Override
    public void onResume() {
        super.onResume();
        getListProduct();
    }

    private void getListProduct() {
        showLoading();
        listProd = new ArrayList<>();
        if (db != null)
            db.collection(AppConstant.PRODUCT).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = document.toObject(Product.class);
                        listProd.add(product);
                    }
                    productHomeAdapter.submitList(listProd);
                    productHomeAdapter.notifyDataSetChanged();
                } else {
                    Log.e("AAA", "Error getting documents: ", task.getException());
                }
                hideLoading();
            });
    }

    @Override
    protected void initAction() {
        productHomeAdapter.setOnItemClickListener((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstant.PRODUCT, listProd.get(position));
            startActivityWithData(ProductDetailActivity.class, bundle);
        });
        binding.layoutHeader.tvAdd.setOnClickListener(view -> navigateTo(R.id.productAddFragment));
    }

}