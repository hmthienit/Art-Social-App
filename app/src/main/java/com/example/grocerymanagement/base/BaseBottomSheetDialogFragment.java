package com.example.grocerymanagement.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grocerymanagement.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Implement common setup here, if any
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Abstract method to provide layout resource ID
    protected abstract int getLayoutResId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BaseBottomSheetDialogTheme);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() != null) {
            BottomSheetDialog sheet = (BottomSheetDialog) getDialog();
            sheet.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        setupViews(view);
    }

    // Method to be implemented by subclasses to setup views
    protected abstract void setupViews(View view);
}
