package com.example.grocerymanagement.views.ui.home.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import com.example.grocerymanagement.R;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentCategoryBinding;
import com.example.grocerymanagement.utils.RecyclerViewUtils;
import com.example.grocerymanagement.views.ui.home.category.adpater.CategoryAdapter;
import com.example.grocerymanagement.views.ui.home.category.model.Category;
import com.example.grocerymanagement.views.ui.home.category.viewmodel.CategoryViewModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment<FragmentCategoryBinding> {

    private CategoryViewModel categoryViewModel;
    private CategoryAdapter categoryAdapter;
    List<Category> listCate;

    @Override
    protected FragmentCategoryBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCategoryBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {

        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        binding.layoutHeader.navHeaderName.setText(R.string.text_category_management);
        categoryAdapter = new CategoryAdapter();
        RecyclerViewUtils.initAdapter(categoryAdapter, binding.revCategory, 0, 1, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getListCategory();
    }

    @Override
    protected void initAction() {
        categoryAdapter.setOnItemClickListener((view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstant.CATEGORIES, listCate.get(position));
            startActivityWithData(CategoryDetailActivity.class, bundle);
        });
        binding.layoutHeader.tvAdd.setOnClickListener(view -> navigateTo(R.id.categoryAddFragment));
    }


    private void getListCategory() {
        showLoading();
        listCate = new ArrayList<>();
        if (db != null)
            db.collection(AppConstant.CATEGORIES).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Category category = document.toObject(Category.class);
                        listCate.add(category); // Display product data

                        Log.e("AAA", "Name: " + category.getCategoryName() + "\n");
                    }
                    categoryAdapter.submitList(listCate);
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    Log.e("AAA", "Error getting documents: ", task.getException());
                }
                hideLoading();
            });
    }
}