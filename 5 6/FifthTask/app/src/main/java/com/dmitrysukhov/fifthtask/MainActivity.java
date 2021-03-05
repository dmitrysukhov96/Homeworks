package com.dmitrysukhov.fifthtask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public interface MainActivityCallback {
        File createImageFile(Context context) throws IOException;
        void addLineToLogFile(String text, Context context);
        void shareLogFile(Context context);
        String getCurrentPhotoPath();
    }

    private boolean clicked = false;
    private FloatingActionButton fabMainAdd;
    private FloatingActionButton fabMainCamera;
    private FloatingActionButton fabMainGallery;
    private FloatingActionButton fabMainShareLog;
    private MainActivityCallback mainActivityCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityCallback = new FileProcessing();
        mainActivityCallback.addLineToLogFile(getString(R.string.on_create) + " ", this);

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
            Intent takeFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(takeFromGallery, FileProcessing.GALLERY_REQUEST_CODE);
        });

        fabMainShareLog = findViewById(R.id.fab_main_share_log);
        fabMainShareLog.setOnClickListener(view -> mainActivityCallback.shareLogFile(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageViewMainPhoto = findViewById(R.id.image_view_main_photo);
        if (requestCode == FileProcessing.CAMERA_REQUEST_CODE && resultCode==RESULT_OK) {
            File capturedFile = new File(mainActivityCallback.getCurrentPhotoPath());
            Glide.with(this).load(capturedFile).into(imageViewMainPhoto);
        }
        if (requestCode == FileProcessing.GALLERY_REQUEST_CODE && resultCode==RESULT_OK) {
            if (data != null) {
                Uri contentUri = data.getData();
                Glide.with(this).load(contentUri).into(imageViewMainPhoto);
            }
        }
    }

    void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = mainActivityCallback.createImageFile(this);
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, getString(R.string.authority), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, FileProcessing.CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainActivityCallback.addLineToLogFile(getString(R.string.on_start) + " ", this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainActivityCallback.addLineToLogFile(getString(R.string.on_resume) + " ", this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainActivityCallback.addLineToLogFile(getString(R.string.on_pause) + " ", this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainActivityCallback.addLineToLogFile(getString(R.string.on_stop) + " ", this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityCallback.addLineToLogFile(getString(R.string.on_destroy) + " ", this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mainActivityCallback.addLineToLogFile(getString(R.string.on_restart) + " ", this);
    }
}