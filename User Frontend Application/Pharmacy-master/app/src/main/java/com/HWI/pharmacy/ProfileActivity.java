package com.HWI.pharmacy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    public static final int CAMERA_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    String currentPhotoPath;
    int flag=0;
    private ImageView userImage;
    private ImageView idImage;
    private TextView one,two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userImage = findViewById(R.id.user_image);
        idImage = findViewById(R.id.user_id);
        one = findViewById(R.id.text_image1);
        two = findViewById(R.id.text_image2);

        idImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();
                flag=2;
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();
                flag=1;
            }
        });
    }

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.postman.com/collections/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    private void askPermission()
    {
         if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
             ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
         }
         else{
             dispatchTakePictureIntent();
         }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CAMERA_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }
            else {
                Toast.makeText(this, "Camera is required!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==CAMERA_REQUEST_CODE && flag==1)
        {
            if(resultCode== Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                String url = Uri.fromFile(f).toString();
                userImage.setImageURI(Uri.parse(url));
                one.setVisibility(View.GONE);
            }
        }
        if(requestCode==CAMERA_REQUEST_CODE && flag==2)
        {
            if(resultCode== Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                String url = Uri.fromFile(f).toString();
                idImage.setImageURI(Uri.parse(url));
                two.setVisibility(View.GONE);
            }
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}