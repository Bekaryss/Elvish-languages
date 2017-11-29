package com.liverkick.elven.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by bekarys on 20.11.17.
 */

public class DownloadHelper {
    private Context context;
    private ProgressDialog dialog;
    private static final String TAG = "Download Task";

    FirebaseStorage storage;
    StorageReference storageRef;

    File apkStorage = null;
    File outputFile = null;

    public DownloadHelper(Context context, String downloadUrl){
        dialog = new ProgressDialog(context);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://elven-c5a79.appspot.com/").child("SINDARIN1.PDF");
        new YourAsyncTask().execute();
    }

    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Downloading..., please wait.");
            dialog.show();
        }

        protected Void doInBackground(Void... args) {
            // do background work here

            //get download file url
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.i("Main", "File uri: " + uri.toString());
                }
            });

            //download the file

            final long ONE_MEGABYTE = 1024 * 1024;
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    File file;
                    FileOutputStream outputStream;
                    try {
                        java.io.File path = new java.io.File(Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                + "/" + "Sindarin.pdf");
                        if (!path.exists()) {
                            outputStream = new FileOutputStream(path);
                            outputStream.write(bytes);
                            outputStream.close();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                     // Handle any errors
                }
            });
            return null;
        }

        protected void onPostExecute(Void result) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
