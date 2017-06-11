package com.cours_openclassroom.pierre.projet_android;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * Created by Pierre on 25/05/2017.
 */

public class photo_partager extends Activity {

    private Button btnTackPic, btnUploadPic;
    private TextView tvHasCamera, tvHasCameraApp;
    private EditText tvNomPic;
    private ImageView ivThumbnailPhoto;
    private Bitmap bitMap;
    private static int TAKE_PICTURE = 1;
    private static Context context;
    private Button retour;
    private StorageReference mStorage;
    private FirebaseDatabase database;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photo_partager.context = getApplicationContext();
        setContentView(R.layout.activity_photo_partager);
        mStorage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        tvHasCamera = (TextView) findViewById(R.id.tvHasCamera);
        tvHasCameraApp = (TextView) findViewById(R.id.tvHasCameraApp);
        tvNomPic = (EditText) findViewById(R.id.tvNomPhoto);
        btnTackPic = (Button) findViewById(R.id.btnTakePic);
        btnUploadPic = (Button) findViewById(R.id.btnUpload);
        ivThumbnailPhoto = (ImageView) findViewById(R.id.ivThumbnailPhoto);
        mProgress = new ProgressDialog(this);
        // Does your device have a camera?
        if (!hasCamera()) {
            tvHasCamera.setBackgroundColor(0xFF00CC00);
            tvHasCamera.setText("Vous n'avez pas d'appareil photo sur votre téléphone");
        }
        // Do you have Camera Apps?
        if (!hasDefaultCameraApp(MediaStore.ACTION_IMAGE_CAPTURE)) {
            tvHasCameraApp.setBackgroundColor(0xFF00CC00);
            tvHasCameraApp.setText("Vous n'avez pas d'application appareil photo sur votre téléphone");
        }

        // add onclick listener to the button
        btnTackPic.setOnClickListener(new OnClickListener() {
            // on button "btnTackPic" is clicked
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getAppContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    askForPermission();
                } else {
                    // create intent with ACTION_IMAGE_CAPTURE action
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKE_PICTURE);
                }
            }
        });

        btnUploadPic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivThumbnailPhoto.getDrawable() == null || tvNomPic.getText().toString().matches("")) {
                    Toast.makeText(photo_partager.this, "Il manque la photo ou le nom de la photo :)", Toast.LENGTH_SHORT).show();
                } else {
                    mProgress.setMessage("Upload en cours ...");
                    mProgress.show();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap btmp = ((BitmapDrawable) ivThumbnailPhoto.getDrawable()).getBitmap();
                    btmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    StorageReference filepath = mStorage.child("Photos").child(tvNomPic.getText().toString());
                    filepath.putBytes(byteArray)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Si réussite à l'accès storage firebase
                                    mProgress.dismiss();
                                    Toast.makeText(photo_partager.this, "Upload terminé :)", Toast.LENGTH_LONG).show();
                                    @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
                                    Log.e("Zaza", "incoming");
                                    Log.e("Zoulou", downloadUri.toString());
//                                    @SuppressWarnings("VisibleForTests") DatabaseReference myRef = database.getReference("tabDLphoto").child(taskSnapshot.getDownloadUrl().toString());
//                                    Image img = new Image(trail.getUnique_id(), downloadUri.toString());
//                                    myRef.setValue(img);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Rajouter affichage si erreur dans accès au Storage
                            mProgress.dismiss();
                            Toast.makeText(photo_partager.this, "Ayayay\n", Toast.LENGTH_LONG).show();
                        }
                    });
                    tvNomPic.setText("");
                    ivThumbnailPhoto.setImageBitmap(null);
                }
            }
        });

        retour = (Button) findViewById(R.id.retour);
        retour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent secondeActivite = new Intent(photo_partager.this, MainActivity.class);

                // Puis on lance l'intent !
                startActivity(secondeActivite);
                //Log.d("test", "------------------------------------------------------------------------------------------------------------------------------ ");

            }
        });
    }

    public static Context getAppContext() {
        return photo_partager.context;
    }

    private void askForPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, 2);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK && intent != null) {
            bitMap = (Bitmap) intent.getExtras().get("data");
            ivThumbnailPhoto.setImageBitmap(bitMap);
        }
    }

    // method to check if you have a Camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    // method to check you have Camera Apps
    private boolean hasDefaultCameraApp(String action) {
        final PackageManager packageManager = getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
