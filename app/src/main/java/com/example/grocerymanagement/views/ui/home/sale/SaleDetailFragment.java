package com.example.grocerymanagement.views.ui.home.sale;


import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.SaleManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.databinding.FragmentSlaeDetailBinding;
import com.example.grocerymanagement.helper.DialogHelper;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;
import com.example.grocerymanagement.views.ui.home.sale.viewmodel.SaleViewModel;

import java.util.Objects;

public class SaleDetailFragment extends BaseFragment<FragmentSlaeDetailBinding> {

    private SaleViewModel saleViewModel;

    @Override
    protected FragmentSlaeDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSlaeDetailBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews() {
        saleViewModel = new ViewModelProvider(requireActivity()).get(SaleViewModel.class);
        Sale sale = saleViewModel.getSale().getValue();
        binding.saleName.setText(Objects.requireNonNull(sale).getSaleName());
        binding.layoutHeader.navHeaderName.setText(sale.getSaleName());
        ImageUtils.generateBarcode(sale.getBarCodeSale(), binding.saleBarcode);
        binding.saleCreatedAt.setText("Created at: " + sale.getCreatedAt());
        binding.saleStatus.setText(sale.getStatus() ? "Status: " + getString(R.string.text_enabled) : "Status: " + getString(R.string.text_disabled));
        binding.saleStartDate.setText("Start date: " + sale.getSaleStartDate());
        binding.saleEndDate.setText("End date: " + sale.getSaleEndDate());
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(view -> requireActivity().finish());
        binding.btnEdit.setOnClickListener(view -> navigateTo(R.id.saleEditFragment));
        binding.btnDelete.setOnClickListener(view -> {
            Sale sale = saleViewModel.getSale().getValue();
            assert sale != null;
            DialogHelper.showConfirmationDialog(requireContext(),
                    "Delete " + sale.getSaleName(),
                    "Would you like to delete the " + sale.getSaleName(),
                    "Delete",
                    (dialogInterface, i) -> deleteSale(sale),
                    "Cancel",
                    (dialogInterface, i) -> dialogInterface.dismiss());
        });
    }

    private void deleteSale(Sale sale) {
        SaleManager saleManager = new SaleManager();
        saleManager.deleteDocument(sale.getId(), unused -> {
                    showToast("The sale " + sale.getSaleName() + " has been delete successfully!");
                    requireActivity().finish();
                },
                e -> showToast("Error when delete the " + sale.getSaleName()));
    }
}