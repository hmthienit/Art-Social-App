package com.example.grocerymanagement.views.ui.home.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.ProductManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentProductDetailBinding;
import com.example.grocerymanagement.helper.DialogHelper;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.category.model.Category;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.home.viewmodel.ProductViewModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ProductDetailFragment extends BaseFragment<FragmentProductDetailBinding> {

    private ProductViewModel productViewModel;

    @Override
    protected FragmentProductDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProductDetailBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initViews() {

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        if (getActivity() instanceof ProductDetailActivity) {
            Product productDetailActivity = productViewModel.getProduct().getValue();
            binding.layoutHeader.navHeaderName.setText(productDetailActivity.getName());
            binding.productName.setText("Name: " + productDetailActivity.getName());
            ImageUtils.generateBarcode(productDetailActivity.getBarCode() + "", binding.barcodeImageView);
            binding.productDescription.setText("Description: " + productDetailActivity.getDescription());
            binding.productPrice.setText("Price: " + productDetailActivity.getPrice());
            binding.productQuantity.setText("Quantity: " + productDetailActivity.getQuantity());
            binding.productBarcode.setText("Barcode: " + productDetailActivity.getBarCode());
            binding.productCreatedAt.setText("Created At: " + productDetailActivity.getCreateAt());
            binding.productWeight.setText("Weight: " + productDetailActivity.getWeight());
            binding.productCategory.setText("Category: " + productDetailActivity.getCategory());
            ImageUtils.getInstance(requireContext()).setImage(binding.productImage, AppConstant.BASE_URL + productDetailActivity.getImage());
//            binding.productBrand.setText("Brand: " + productDetailActivity.product.getCategory());

            Log.e("AVCS", "Test  viewmodel: " + productViewModel.getProduct().getValue().getDescription());
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
                Navigation.findNavController(requireView()).navigate(R.id.productEditFragment);
            }
        });
        binding.btnDelete.setOnClickListener(view -> {
            Product product = productViewModel.getProduct().getValue();
            assert product != null;
            DialogHelper.showConfirmationDialog(requireContext(),
                    "Delete " + product.getName(),
                    "Would you like to delete the " + product.getName(),
                    "Delete",
                    (dialogInterface, i) -> deleteProduct(product),
                    "Cancel",
                    (dialogInterface, i) -> dialogInterface.dismiss());
        });
    }

    private void deleteProduct(Product product) {
        ProductManager productManager = new ProductManager();
        productManager.deleteDocument(product.getId(), unused -> {
                    showToast("The product " + product.getName() + " has been deleted successfully!");
                    requireActivity().finish();
                },
                e -> showToast("Error while deleted the product " + product.getName())
        );
    }
}