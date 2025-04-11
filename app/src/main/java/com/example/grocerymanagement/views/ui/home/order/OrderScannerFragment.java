package com.example.grocerymanagement.views.ui.home.order;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.ViewGroup;

import com.example.grocerymanagement.api.OrderManager;
import com.example.grocerymanagement.api.ProductManager;
import com.example.grocerymanagement.base.BaseFragment;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.FragmentOrderScannerBinding;
import com.example.grocerymanagement.views.ui.home.home.model.Product;
import com.example.grocerymanagement.views.ui.home.home.viewmodel.ProductViewModel;
import com.example.grocerymanagement.views.ui.home.order.viewmodel.OrderViewModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.firestore.FieldValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class OrderScannerFragment extends BaseFragment<FragmentOrderScannerBinding> {

    private CameraSource cameraSource;
    private final int requestCode = 1001;
    OrderViewModel orderViewModel;
    ProductViewModel productViewModel;

    @Override
    protected FragmentOrderScannerBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentOrderScannerBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setupBarcodeScanner();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, requestCode);
        }
    }

    @Override
    protected void initAction() {

    }


    private void setupBarcodeScanner() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(requireContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(requireContext(), barcodeDetector).setAutoFocusEnabled(true).build();
        binding.cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(binding.cameraPreview.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    Log.e("SAASAS", barcodes.valueAt(0).displayValue + " hdhdhhdhdhdh");
                    getProdById("tAvUcRYbaeIb0SUchnCy");
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            popBackStack();
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == this.requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupBarcodeScanner();
            } else {
                showToast("Failed");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private void addToListProductOrder() {
        OrderManager orderManager = new OrderManager();
        Map<String, Object> product = new HashMap<>();
        product.put(AppConstant.ORDER_LIST_PRDD, FieldValue.arrayUnion(productViewModel.getProduct().getValue()));
        orderManager.updateDocument(orderViewModel.getOrder().getValue().getId(), product, unused -> {
//            popBackStack();
        }, e -> {
        });
    }

    private void getProdById(String id) {
        ProductManager productManager = new ProductManager();
        productManager.getDocument(id, Product.class, documentSnapshot -> {
            productViewModel.setProduct(documentSnapshot.toObject(Product.class));
            Log.e("ASAAA", Objects.requireNonNull(documentSnapshot.toObject(Product.class)).getName());
            addToListProductOrder();
        }, e -> {
            showToast("Failed to get product" + id);
        });
    }
}