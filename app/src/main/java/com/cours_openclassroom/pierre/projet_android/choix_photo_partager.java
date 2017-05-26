package com.cours_openclassroom.pierre.projet_android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.view.View.OnClickListener;


/**
 * Created by Pierre on 25/05/2017.
 */

public class choix_photo_partager extends Activity implements OnClickListener {

    Button btnTackPic;
    TextView tvHasCamera, tvHasCameraApp;
    ImageView ivThumbnailPhoto;
    Bitmap bitMap;
    static int TAKE_PICTURE = 1;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        choix_photo_partager.context = getApplicationContext();
        setContentView(R.layout.photo_partager);

        tvHasCamera = (TextView) findViewById(R.id.tvHasCamera);
        tvHasCameraApp = (TextView) findViewById(R.id.tvHasCameraApp);
        btnTackPic = (Button) findViewById(R.id.btnTakePic);
        ivThumbnailPhoto = (ImageView) findViewById(R.id.ivThumbnailPhoto);
        // Does your device have a camera?
        if (hasCamera()) {
            tvHasCamera.setBackgroundColor(0xFF00CC00);
            tvHasCamera.setText("You have Camera");
        }
        // Do you have Camera Apps?
        if (hasDefualtCameraApp(MediaStore.ACTION_IMAGE_CAPTURE)) {
            tvHasCameraApp.setBackgroundColor(0xFF00CC00);
            tvHasCameraApp.setText("You have Camera Apps");
        }
        // add onclick listener to the button
        btnTackPic.setOnClickListener(this);
    }

    public static Context getAppContext() {
        return choix_photo_partager.context;
    }


    // on button "btnTackPic" is clicked
    @Override
    public void onClick(View view) {

        if (ActivityCompat.checkSelfPermission(getAppContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            askForPermission();
        } else {
            // create intent with ACTION_IMAGE_CAPTURE action
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, TAKE_PICTURE);
        }
    }


    private void askForPermission()
    {
        requestPermissions(new String[] { Manifest.permission.CAMERA }, 2);
        requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 2);
        requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK && intent != null) {
            // get bundle

            Bundle extras = intent.getExtras();
            // get bitmap
            bitMap = (Bitmap) extras.get("data");
            ivThumbnailPhoto.setImageBitmap(bitMap);

            StoreByteImage(bitMap, 100); // quality entre 0 et 100

        }
    }

    // method to check if you have a Camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    // method to check you have Camera Apps
    private boolean hasDefualtCameraApp(String action) {
        final PackageManager packageManager = getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    public boolean StoreByteImage(Bitmap myImage, int quality) {
        FileOutputStream fileOutputStream = null;

        File sdCard = Environment.getExternalStorageDirectory();
        File file = new File(sdCard, "photo.jpg");
        try {
            fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            myImage.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            Log.d("test", "------------------------------------------------------------------------------------------------------------------------------ ");

        } catch (FileNotFoundException e) {
            Log.i("EXCP FNF", e.getMessage());
        } catch (IOException e) {
            Log.i("EXCP IO", e.getMessage());
        }
        return true;
    }


}
