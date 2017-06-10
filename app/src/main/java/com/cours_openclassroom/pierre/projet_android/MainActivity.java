package com.cours_openclassroom.pierre.projet_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends Activity {
    private Button Partager;
    private Button mUplaodBtn;
    private ImageView mImageView;


    private static final int CAMERA_REQUEST_CODE = 1;
    Uri uri;

    private StorageReference mStorage;
    private ProgressDialog mProgress;

    TextView Liste_des_photos = null;
    TextView Titre_colonne_photo = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorage = FirebaseStorage.getInstance().getReference();

        // On récupère toutes les vues dont on a besoin
        Partager = (Button) findViewById(R.id.Button_partager);
        Partager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent secondeActivite = new Intent(MainActivity.this, choix_photo_partager.class);

                // Puis on lance l'intent !
                startActivity(secondeActivite);
                //Log.d("test", "------------------------------------------------------------------------------------------------------------------------------ ");

            }
        });

        mUplaodBtn = (Button) findViewById(R.id.Bupload);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mProgress = new ProgressDialog(this);

        mUplaodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (uri != null) {
            outState.putString("cameraImageUri", uri.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("cameraImageUri")) {
            uri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        onRestoreInstanceState(data.getExtras());

        Log.e("REPONDS", "Réponds peut être?");


        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Log.e("DATA", "Intent data is different from null -> " + data);
                mProgress.setMessage("Upload en cours ...");
                mProgress.show();

                //Log.d("KEZOKOKO", data.getData().getLastPathSegment());

                //uri = data.getData();

                Log.e("YOYOYOYO", uri.toString());
                Bitmap image = (Bitmap) data.getExtras().get("data");

                mImageView.setImageBitmap(image);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                TextView mListePhoto = (TextView) findViewById(R.id.Liste_des_photos);

//              CONSTAT: "data" à l'origine du problème. Type abstract mais lors de l'appel de onActivityResult pb sur getData -> null pointer exception relevée

                StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
                mListePhoto.setText(filepath.toString());
                Log.e("WUUUUUUUUUUUUUUUUUUUUUU", filepath.toString());

                // NOUVEAU & DERNIER PB (inch) ici!! l'ajout ".putFile(uri) ne marche pas

                filepath.putBytes(byteArray)
                        //.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Si réussite à l'accès storage firebase

                                Log.e("Succès", "Youpla");

                                mProgress.dismiss();

                                Toast.makeText(MainActivity.this, "Upload terminé :)", Toast.LENGTH_LONG).show();
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Rajouter affichage si erreur dans accès au Storage
                        mProgress.dismiss();
                        Toast.makeText(MainActivity.this, "Ayayay\n" + uri.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Tristitude", e.getMessage());
                    }
                });

            } else {
                Log.e("NO DATA", "Intent data equals null");
            }
        }

    }

}
