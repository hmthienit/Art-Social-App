package com.example.grocerymanagement.views.ui.home.staff;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.StaffManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.databinding.FragmentStaffAddBinding;
import com.example.grocerymanagement.views.ui.home.staff.adapter.PositionSpinnerAdapter;
import com.example.grocerymanagement.views.ui.home.staff.model.Staff;
import com.example.grocerymanagement.views.ui.home.staff.viewmodel.StaffViewModel;

import java.util.Date;
import java.util.List;


public class StaffAddFragment extends BaseFragment<FragmentStaffAddBinding> {

    private StaffViewModel staffViewModel;


    @Override
    protected FragmentStaffAddBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentStaffAddBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        staffViewModel = new ViewModelProvider(requireActivity()).get(StaffViewModel.class);

        binding.layoutHeader.navHeaderName.setText(R.string.text_add_staff);
        getListPosition();
    }

    @Override
    protected void initAction() {
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
        binding.layoutHeader.icBack.setOnClickListener(view -> popBackStack());
        binding.btnCancel.setOnClickListener(view -> popBackStack());
        binding.btnSave.setOnClickListener(view -> addStaffToFireStore());
    }

    private void getListPosition() {
        PositionSpinnerAdapter positionSpinnerAdapter = new PositionSpinnerAdapter(requireContext(), List.of(getResources().getStringArray(R.array.position)));
        binding.spnStaffPosition.setAdapter(positionSpinnerAdapter);
    }

    private void addStaffToFireStore() {
        StaffManager staffManager = new StaffManager();
        String staffName = binding.editStaffName.getText().toString();
        String staffPosition = staffViewModel.getPositionName().getValue() != null ? staffViewModel.getPositionName().getValue() : getResources().getStringArray(R.array.position)[0];
        String staffCodeNumber = binding.editStaffCodeNumber.getText().toString();
        String staffAccountName = binding.editStaffAccount.getText().toString();
        boolean staffStatus = binding.staffEnableSwitch.isChecked();
        String createdAt = new Date().toString();
        Staff staff = new Staff("", staffName, staffPosition, staffCodeNumber, staffAccountName, staffStatus, createdAt, "");
        staffManager.addDocument(staff,
                documentReference -> {
                    String generatedId = documentReference.getId();
                    staff.setId(generatedId);
                    documentReference.set(staff);
                    showToast("Staff added successfully!");
                    popBackStack();
                },
                e -> {
                    showToast("Error when added the staff name " + staffName);
                });
    }


}