package com.example.grocerymanagement.views.ui.home.social_post;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.grocerymanagement.R;
import com.example.grocerymanagement.api.config.CloudinaryConfig;
import com.example.grocerymanagement.base.BaseActivity;
import com.example.grocerymanagement.constants.AppConstant;
import com.example.grocerymanagement.databinding.ActivitySocialPostBinding;
import com.example.grocerymanagement.utils.ImageUtils;
import com.example.grocerymanagement.views.ui.home.social_post.model.TestImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class SocialPostActivity extends BaseActivity<ActivitySocialPostBinding> {

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int IMAGE_PICK_REQUEST_CODE = 101;
    private String selectedFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CloudinaryConfig.initCloudinary();
    }

    @Override
    protected ActivitySocialPostBinding getViewBinding() {
        return ActivitySocialPostBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        checkAndRequestPermissions();
    }

    @Override
    protected void initAction() {

        binding.btUpload.setOnClickListener(view ->
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE);

        });

    }

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        } else {
            pickImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            selectedFilePath = getPathFromUri(imageUri);
            Log.d("AAA", "File path " + selectedFilePath);
            binding.image.setImageURI(imageUri);
            uploadImageToCloudinary(selectedFilePath);
        }
    }

    private String getPathFromUri(Uri uri) {
        try {
            File file = new File(getCacheDir(), "temp_image");
            try (InputStream inputStream = getContentResolver().openInputStream(uri);
                 OutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void uploadImageToCloudinary(String filePath) {
        if (filePath == null) {
            Toast.makeText(this, "File path is invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        MediaManager.get().upload(filePath)
                .option("folder", "social_posts") // Optional: Upload to a specific folder in Cloudinary
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Log.d("Cloudinary", "Upload started");
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        double progress = (double) bytes / totalBytes * 100;
                        Log.d("Cloudinary", "Upload progress: " + progress + "%");
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        Log.d("Cloudinary", "Upload successful! URL: " + resultData.get("secure_url"));
                        showToast("Uploaded Successfully!");
                        addImageToDatabase(Objects.requireNonNull(resultData.get("secure_url")).toString());
                        ImageUtils.getInstance(getApplicationContext()).setImage(binding.imageAfterUploaded, Objects.requireNonNull(resultData.get("secure_url")).toString());
                    }

                    @Override
                    public void onError(String requestId, com.cloudinary.android.callback.ErrorInfo error) {
                        Log.e("Cloudinary", "Upload failed: " + error.getDescription());
                        showToast("Upload Failed!");
                    }

                    @Override
                    public void onReschedule(String requestId, com.cloudinary.android.callback.ErrorInfo error) {
                        Log.e("Cloudinary", "Upload rescheduled: " + error.getDescription());
                    }
                })
                .dispatch();
    }

    public void addImageToDatabase(String imageUrl) {
        // Add your code to save the image URL to your database
        // For example, you can create a new TestImage object and save it to Firestore
        TestImage testImage = new TestImage("", imageUrl, new Date().toString());
        db.collection(AppConstant.TEST_IMAGE).add(testImage).addOnSuccessListener(documentReference -> {
            String documentId = documentReference.getId();
            testImage.setId(documentId);
            documentReference.set(testImage);
            showToast("Image added with URL: " + testImage.getImageUrl());
            fetchImageById(documentId);

        }).addOnFailureListener(e -> {
            showToast("Error adding image: " + e.getMessage());
        });
    }

    private void fetchImageById(String documentId) {
        db.collection(AppConstant.TEST_IMAGE)
                .document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String imageUrl = documentSnapshot.getString("imageUrl");
                        if (imageUrl != null) {
                            ImageUtils.getInstance(getApplicationContext())
                                    .setImage(binding.imageAfterUploadedToFirebase, imageUrl);
                        } else {
                            showToast("No image URL found for this ID");
                        }
                    } else {
                        showToast("No document found with this ID");
                    }
                })
                .addOnFailureListener(e -> showToast("Error fetching image: " + e.getMessage()));
    }

}