package com.example.grocerymanagement.api;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("ImagesStorage/upload_images.php")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);
}
