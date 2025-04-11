package com.example.grocerymanagement.views.ui.home.staff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.lifecycle.ViewModelProvider;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.StaffManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentStaffEditBinding;
import com.example.grocerymanagement.views.ui.home.staff.adapter.PositionSpinnerAdapter;
import com.example.grocerymanagement.views.ui.home.staff.model.Staff;
import com.example.grocerymanagement.views.ui.home.staff.viewmodel.StaffViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StaffEditFragment extends BaseFragment<FragmentStaffEditBinding> {
    StaffViewModel staffViewModel;

    @Override
    protected FragmentStaffEditBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentStaffEditBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        staffViewModel = new ViewModelProvider(requireActivity()).get(StaffViewModel.class);
        Staff staff = staffViewModel.getStaff().getValue();
        if (staff != null) {
            binding.edtStaffName.setText(staff.getStaffName());
            binding.edtStaffAccount.setText(staff.getStaffAccount());
            binding.edtStaffCode.setText(staff.getStaffCodeNumber());
            binding.staffEnableSwitch.setChecked(staff.isEnable());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getListPosition();
    }

    @Override
    protected void initAction() {
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
        binding.btnCancel.setOnClickListener(view -> popBackStack());

        binding.spnStaffPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String staffPosition = (String) adapterView.getItemAtPosition(i);
                staffViewModel.setPositionName(staffPosition);
                showToast(staffViewModel.getPositionName().getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnSave.setOnClickListener(view -> editStaff());
    }

    private void editStaff() {
        StaffManager staffManager = new StaffManager();
        Map<String, Object> staff = new HashMap<>();
        staff.put(AppConstant.STAFF_NAME, binding.edtStaffName.getText().toString());
        staff.put(AppConstant.STAFF_CODE_NUMBER, binding.edtStaffCode.getText().toString());
        staff.put(AppConstant.STAFF_ACCOUNT, binding.edtStaffAccount.getText().toString());
        staff.put(AppConstant.STAFF_ENABLE, binding.staffEnableSwitch.isChecked());
        staff.put(AppConstant.STAFF_POSITION, staffViewModel.getPositionName().getValue() != null ? staffViewModel.getPositionName().getValue() : getResources().getStringArray(R.array.position)[0]);
        staffManager.updateDocument(Objects.requireNonNull(staffViewModel.getStaff().getValue()).getId(), staff, aVoid -> {
            showToast("Update the staff " + binding.edtStaffName.getText().toString() + " successfully!");
            requireActivity().finish();
        }, e -> {
            showToast("Failed when update the " + binding.edtStaffName.getText().toString());
        });
    }

    private void getListPosition() {
        PositionSpinnerAdapter positionSpinnerAdapter = new PositionSpinnerAdapter(requireContext(), List.of(getResources().getStringArray(R.array.position)));
        binding.spnStaffPosition.setAdapter(positionSpinnerAdapter);
    }
}