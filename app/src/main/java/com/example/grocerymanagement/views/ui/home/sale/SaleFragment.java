package com.example.grocerymanagement.views.ui.home.sale;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseAdapter;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentSaleBinding;
import com.example.grocerymanagement.utils.RecyclerViewUtils;
import com.example.grocerymanagement.views.ui.home.home.ProductDetailActivity;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.sale.adapter.SaleAdapter;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SaleFragment extends BaseFragment<FragmentSaleBinding> {

    private SaleViewModel mViewModel;
    private SaleAdapter saleAdapter;
    List<Sale> listSale;

    @Override
    protected FragmentSaleBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSaleBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        binding.layoutHeader.navHeaderName.setText(R.string.text_sale_management);
        saleAdapter = new SaleAdapter(requireContext());
        RecyclerViewUtils.initAdapter(saleAdapter, binding.revSale, 0, 1, false);
    }

    @Override
    protected void initAction() {
        saleAdapter.setOnItemClickListener((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstant.SALES, listSale.get(position));
            startActivityWithData(SaleDetailActivity.class, bundle);
        });
        binding.layoutHeader.tvAdd.setOnClickListener(view -> navigateTo(R.id.saleAddFragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        getListProduct();
    }

    private void getListProduct(){
        showLoading();
        listSale = new ArrayList<>();
        if (db != null)
            db.collection(AppConstant.SALES).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Sale sale = document.toObject(Sale.class);
                        listSale.add(sale); // Display product data
                    }
                    saleAdapter.submitList(listSale);
                    saleAdapter.notifyDataSetChanged();
                } else {
                    Log.e("AAA", "Error getting documents: ", task.getException());
                }
                hideLoading();
            });
    }

}