package com.example.grocerymanagement.views.ui.home.staff;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.StaffManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentStaffBinding;
import com.example.grocerymanagement.utils.RecyclerViewUtils;
import com.example.grocerymanagement.views.ui.home.sale.SaleDetailActivity;
import com.example.grocerymanagement.views.ui.home.staff.adapter.StaffAdapter;
import com.example.grocerymanagement.views.ui.home.staff.model.Staff;
import com.example.grocerymanagement.views.ui.home.staff.viewmodel.StaffViewModel;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StaffFragment extends BaseFragment<FragmentStaffBinding> {

    private StaffAdapter staffAdapter;
    private StaffViewModel staffViewModel;
    List<Staff> listStaffs;

    @Override
    protected FragmentStaffBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentStaffBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        staffViewModel = new ViewModelProvider(requireActivity()).get(StaffViewModel.class);
        binding.layoutHeader.navHeaderName.setText(R.string.text_staff_management);

        staffAdapter = new StaffAdapter();
        RecyclerViewUtils.initAdapter(staffAdapter, binding.revStaff, 0, 1, false);
    }

    @Override
    protected void initAction() {
        staffAdapter.setOnItemClickListener((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstant.STAFFS, listStaffs.get(position));
            startActivityWithData(StaffDetailActivity.class, bundle);
        });
        binding.layoutHeader.tvAdd.setOnClickListener(view -> navigateTo(R.id.staffAddFragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        getListStaff();
    }

    private void getListStaff() {
        showLoading();
        listStaffs = new ArrayList<>();
        StaffManager staffManager = new StaffManager();
        staffManager.getAllDocuments(
                Staff.class,
                queryDocumentSnapshots -> {
                    hideLoading();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Staff staff = document.toObject(Staff.class);
                        listStaffs.add(staff);
                    }
                    staffAdapter.submitList(listStaffs);
                    staffAdapter.notifyDataSetChanged();
                },
                e -> {
                    hideLoading();
                    showToast("Error when display list staffs");
                });
    }

}