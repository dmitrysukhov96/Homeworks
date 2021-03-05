package com.dmitrysukhov.fifthtask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileProcessing {

    private static final String FILE_NAME = "lifecycle_logs.txt";
    private static String currentPhotoPath;
    public static final int CAMERA_REQUEST_CODE = 52;
    public static final int GALLERY_REQUEST_CODE = 25;
    private static final String DATE_FORMAT = "yyyyMMdd_HHmmss";

    public static File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static void addLineToLogFile(String text, Context context) {
        File lifecycleLogsFile = new File(context.getFilesDir(), FILE_NAME);
        if (!lifecycleLogsFile.exists()) {
            try {
                boolean justForNotIgnoringResult = lifecycleLogsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(lifecycleLogsFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shareLogFile(Context context) {
        File lifecycleLogsFile = new File(context.getFilesDir(), FILE_NAME);
        if (!lifecycleLogsFile.exists()) {
            Toast.makeText(context, context.getString(R.string.file_not_found), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            Uri uri = FileProvider.getUriForFile(context,
                    context.getString(R.string.authority), new File(context.getFilesDir(), FILE_NAME));
            if (uri != null) {
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType(context.getString(R.string.intent_type));
                intentShare.putExtra(Intent.EXTRA_STREAM, uri);
                intentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(intentShare, context.getString(R.string.chooser_title)));
            }
        } catch (IllegalArgumentException e) {
            Log.e(FILE_NAME,
                    context.getString(R.string.file_cant_be_shared) + lifecycleLogsFile.toString());
        }
    }

    public static String getCurrentPhotoPath() {
        return currentPhotoPath;
    }
}
