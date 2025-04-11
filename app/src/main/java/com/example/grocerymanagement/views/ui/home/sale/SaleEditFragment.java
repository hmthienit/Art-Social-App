package com.example.grocerymanagement.views.ui.home.sale;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentSaleEditBinding;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;
import com.example.grocerymanagement.views.ui.home.sale.viewmodel.SaleViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SaleEditFragment extends BaseFragment<FragmentSaleEditBinding> {

    private SaleViewModel saleViewModel;

    @Override
    protected FragmentSaleEditBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSaleEditBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        saleViewModel = new ViewModelProvider(requireActivity()).get(SaleViewModel.class);
        Sale sale = saleViewModel.getSale().getValue();
        if (sale != null) {
            binding.layoutHeader.navHeaderName.setText(sale.getSaleName());
            binding.edtSaleName.setText(sale.getSaleName());
            binding.edtStartDate.setText(sale.getSaleStartDate());
            binding.edtEndDate.setText(sale.getSaleEndDate());
            binding.edtSaleBarCode.setText(sale.getBarCodeSale() + "");
            binding.edtSaleValue.setText(sale.getSaleValue() + "");
            binding.saleStatus.setChecked(sale.getStatus());
        }

    }

    @Override
    protected void initAction() {
        binding.edtStartDate.setOnClickListener(view -> datePickerUtils.showDatePickerDialog(binding.edtStartDate));
        binding.edtEndDate.setOnClickListener(view -> datePickerUtils.showDatePickerDialog(binding.edtEndDate));
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
        binding.btnCancel.setOnClickListener(view -> popBackStack());
        binding.btnSave.setOnClickListener(view -> {
            Map<String, Object> sale =  new HashMap<>();
            sale.put(AppConstant.SALE_NAME, binding.edtSaleName.getText().toString());
            sale.put(AppConstant.SALE_STATUS, binding.saleStatus.isChecked());
            sale.put(AppConstant.SALE_VALUE, Integer.parseInt(binding.edtSaleValue.getText().toString()));
            sale.put(AppConstant.SALE_START_DATE, binding.edtStartDate.getText().toString());
            sale.put(AppConstant.SALE_END_DATE, binding.edtEndDate.getText().toString());
            sale.put(AppConstant.SALE_BAR_CODE, binding.edtSaleBarCode.getText().toString());
            editSale(Objects.requireNonNull(saleViewModel.getSale().getValue()).getId(), sale);
        });
    }

    public void editSale(String saleID, Map<String, Object> sale) {

        db.collection(AppConstant.SALES).document(String.valueOf(saleID)).update(sale).addOnSuccessListener(aVoid -> {
            showToast(getString(R.string.text_sale_updated_successfully));
            backToPrevActivity();
        }).addOnFailureListener(e -> {
            showToast(getString(R.string.text_failed_to_update_sale));
        });

    }
}