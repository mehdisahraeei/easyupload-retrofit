package com.mahdi.uploadretrofit.api;

import com.mahdi.uploadretrofit.Model.model;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiServices {

    @POST("fileupload.php")
    @FormUrlEncoded
    Call<model> uploadImage(@Field("t1") String title,@Field("upload") String image);


}
