package com.mahdi.uploadretrofit;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.mahdi.uploadretrofit.Model.model;
import com.mahdi.uploadretrofit.api.ApiServices;
import com.mahdi.uploadretrofit.api.RetrofitClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {



    private Button buttonUpload, buttonSelect;
    private ImageView imageView;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUpload = findViewById(R.id.upload);
        buttonSelect = findViewById(R.id.load);
        imageView = findViewById(R.id.image);


        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                resultLauncher.launch(intent);
            }
        });


        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "please pick a image...", Toast.LENGTH_SHORT).show();
                try {
                    Uploading();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }




    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                Uri uri = intent.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });




    public void Uploading() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        String name = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String image = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

        ApiServices apiServices = RetrofitClient.getApiServices();
        Call<model> upload = apiServices.uploadImage(name, image);

        upload.enqueue(new Callback<model>() {
            @Override
            public void onResponse(Call<model> call, Response<model> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<model> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
