package com.example.grocerymanagement.views.ui.home.sale;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentSaleAddBinding;
import com.example.grocerymanagement.utils.DatePickerUtils;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.sale.model.Sale;

import java.util.Date;


public class SaleAddFragment extends BaseFragment<FragmentSaleAddBinding> {

    @Override
    protected FragmentSaleAddBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSaleAddBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initAction() {
        binding.editSaleStartDate.setOnClickListener(view -> new DatePickerUtils(requireContext()).showDatePickerDialog(binding.editSaleStartDate));
        binding.editSaleEndDate.setOnClickListener(view -> new DatePickerUtils(requireContext()).showDatePickerDialog(binding.editSaleEndDate));
        binding.btnCancel.setOnClickListener(view -> popBackStack());
        binding.btnSave.setOnClickListener(view -> {
            String saleName = binding.editSaleName.getText().toString();
            String createdAt = new Date().toString();
            boolean status = binding.saleEnableSwitch.isChecked();
            String startDate = binding.editSaleStartDate.getText().toString();
            String endDate = binding.editSaleEndDate.getText().toString();
            String barCode = binding.editSaleBarcode.getText().toString();
            int saleValue = Integer.parseInt(binding.editSaleValue.getText().toString());
            Sale sale = new Sale("", saleName, status, createdAt, startDate, endDate, barCode, saleValue);
            addSaleToFireStore(sale);
        });
    }

    private void addSaleToFireStore(Sale sale) {
        db.collection(AppConstant.SALES).add(sale).addOnSuccessListener(documentReference -> {
            sale.setId(documentReference.getId());
            documentReference.set(sale);
            showToast("Sale added successfully!");
            popBackStack();
        }).addOnFailureListener(e -> {
            showToast("Error adding sale: " + e);
        });
    }
}