package com.dmitrysukhov.fifthtask;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private boolean clicked = false;
    private FloatingActionButton fabMainAdd;
    private FloatingActionButton fabMainCamera;
    private FloatingActionButton fabMainGallery;
    private FloatingActionButton fabMainShareLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileProcessing.addLineToLogFile(getString(R.string.on_create) + " ", this);
        fabMainAdd = findViewById(R.id.fab_main_add);
        fabMainAdd.setOnClickListener(view -> {
            if (!clicked) {
                fabMainCamera.setVisibility(View.VISIBLE);
                fabMainGallery.setVisibility(View.VISIBLE);
                fabMainShareLog.setVisibility(View.VISIBLE);
                fabMainCamera.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_bottom_anim));
                fabMainGallery.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_bottom_anim));
                fabMainShareLog.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_bottom_anim));
                fabMainAdd.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_open_anim));
                fabMainCamera.setClickable(true);
                fabMainGallery.setClickable(true);
                fabMainShareLog.setClickable(true);
            } else {
                fabMainCamera.setVisibility(View.INVISIBLE);
                fabMainGallery.setVisibility(View.INVISIBLE);
                fabMainShareLog.setVisibility(View.INVISIBLE);
                fabMainCamera.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.to_bottom_anim));
                fabMainGallery.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.to_bottom_anim));
                fabMainShareLog.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.to_bottom_anim));
                fabMainAdd.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_close_anim));
                fabMainCamera.setClickable(false);
                fabMainGallery.setClickable(false);
                fabMainShareLog.setClickable(false);
            }
            clicked = !clicked;
        });

        fabMainCamera = findViewById(R.id.fab_main_camera);
        fabMainCamera.setOnClickListener(view -> {
            try {
                dispatchTakePictureIntent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fabMainGallery = findViewById(R.id.fab_main_gallery);
        fabMainGallery.setOnClickListener(view -> {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(this, permission, 1);
                }} else {
                    Intent takeFromGallery = new Intent(Intent.ACTION_PICK);
                    takeFromGallery.setType(getString(R.string.image_type));
                    takeFromGallery.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(takeFromGallery, FileProcessing.GALLERY_REQUEST_CODE);
                }
            // Intent takeFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // startActivityForResult(takeFromGallery, FileProcessing.GALLERY_REQUEST_CODE);
        });

        fabMainShareLog = findViewById(R.id.fab_main_share_log);
        fabMainShareLog.setOnClickListener(view -> FileProcessing.shareLogFile(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent takeFromGallery = new Intent(Intent.ACTION_PICK);
            takeFromGallery.setType(getString(R.string.image_type));
            takeFromGallery.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(takeFromGallery, FileProcessing.GALLERY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageViewMainPhoto = findViewById(R.id.image_view_main_photo);
        if (requestCode == FileProcessing.CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            File capturedFile = new File(FileProcessing.getCurrentPhotoPath());
            Glide.with(this).load(capturedFile).into(imageViewMainPhoto);
        }
        if (requestCode == FileProcessing.GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                // Uri contentUri = data.getData();
                //  Glide.with(this).load(contentUri).into(imageViewMainPhoto);
                try {
                    Uri contentUri = data.getData();
                    String [] filePathColumn = {MediaStore.Images.ImageColumns.DATA};
                    if (contentUri!=null){
                        Cursor cursor = getContentResolver().query(contentUri,filePathColumn,null,null,null);
                        if (cursor!=null){
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            Glide.with(this).load(picturePath).fitCenter().into(imageViewMainPhoto);
                        }
                        else{
                            Glide.with(this).load(contentUri).fitCenter().into(imageViewMainPhoto);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = FileProcessing.createImageFile(this);
            Uri photoURI = FileProvider.getUriForFile(this, getString(R.string.authority), photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, FileProcessing.CAMERA_REQUEST_CODE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FileProcessing.addLineToLogFile(getString(R.string.on_start) + " ", this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FileProcessing.addLineToLogFile(getString(R.string.on_resume) + " ", this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileProcessing.addLineToLogFile(getString(R.string.on_pause) + " ", this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FileProcessing.addLineToLogFile(getString(R.string.on_stop) + " ", this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileProcessing.addLineToLogFile(getString(R.string.on_destroy) + " ", this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FileProcessing.addLineToLogFile(getString(R.string.on_restart) + " ", this);
    }
}