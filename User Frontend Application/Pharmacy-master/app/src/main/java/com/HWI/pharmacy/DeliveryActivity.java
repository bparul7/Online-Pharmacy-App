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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeliveryActivity extends AppCompatActivity {

    private LottieAnimationView anim;
    private TextView text;
    public static final int CAMERA_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliver_activity);

        anim = findViewById(R.id.anim);
        text = findViewById(R.id.del_text);
        int flag = getIntent().getIntExtra("flag",1);

        if(flag==1)
        {
            anim.setAnimation("okay.json");
            text.setText("Will be Delivered");
        }
        else
        {
            askPermission();
        }

    }

    private void askPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
        }
        else{
            dispatchTakePictureIntent();
        }
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
        if(requestCode==CAMERA_REQUEST_CODE)
        {
            if(resultCode== Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                String url = Uri.fromFile(f).toString();
                anim.setAnimation("no.json");
                text.setText("Not Advised");
            }
        }
    }
}