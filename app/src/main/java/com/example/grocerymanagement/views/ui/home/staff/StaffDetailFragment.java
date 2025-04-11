package com.example.grocerymanagement.views.ui.home.staff;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.StaffManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.databinding.FragmentStaffDetailBinding;
import com.example.grocerymanagement.helper.DialogHelper;
import com.example.grocerymanagement.views.ui.home.staff.model.Staff;
import com.example.grocerymanagement.views.ui.home.staff.viewmodel.StaffViewModel;

import java.util.Objects;

public class StaffDetailFragment extends BaseFragment<FragmentStaffDetailBinding> {

    private StaffViewModel staffViewModel;

    @Override
    protected FragmentStaffDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentStaffDetailBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews() {
        staffViewModel = new ViewModelProvider(requireActivity()).get(StaffViewModel.class);
        Staff staff = staffViewModel.getStaff().getValue();
        if (staff != null) {
            binding.tvStaffId.setText("ID: " + staff.getId());
            binding.tvStaffAccount.setText("Staff account: " + staff.getStaffAccount());
            binding.tvStaffName.setText("Staff name: " + staff.getStaffName());
            binding.tvStaffPosition.setText("Staff position: " + staff.getStaffPosition());
            binding.tvStaffCodeNumber.setText("Staff code number: " + staff.getStaffCodeNumber());
            binding.tvStaffIsEnable.setText("Staff status: " + (staff.isEnable() ? getString(R.string.text_enabled) : getString(R.string.text_disabled)));
            binding.tvCreateAt.setText("Staff created at: " + staff.getCreateAt());
            binding.layoutHeader.navHeaderName.setText(staff.getStaffName());
        }
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().finish();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(R.id.staffEditFragment);
            }
        });

        binding.btnDelete.setOnClickListener(view -> {
            DialogHelper.showConfirmationDialog(requireContext(), "Delete " + staffViewModel.getStaff().getValue().getStaffName(), "Would you like to delete the " + staffViewModel.getStaff().getValue().getStaffName() + "?", "Delete", (dialogInterface, i) -> {
                deleteStaff();
            }, "Cancel", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
        });
    }

    private void deleteStaff() {
        StaffManager staffManager = new StaffManager();
        staffManager.deleteDocument(Objects.requireNonNull(staffViewModel.getStaff().getValue()).getId(), unused -> {
                    showToast("Delete " + staffViewModel.getStaff().getValue().getStaffName() + " successfully!");
                    requireActivity().finish();
                },
                e -> {
                    showToast("Failed to delete " + staffViewModel.getStaff().getValue().getStaffName());
                });
    }
}