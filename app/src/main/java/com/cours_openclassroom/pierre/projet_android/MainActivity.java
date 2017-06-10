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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {
    private static final int CAMERA_REQUEST_CODE = 1;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private ImageView mImageView;

    TextView Liste_des_photos = null;
    TextView Titre_colonne_photo = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorage = FirebaseStorage.getInstance().getReference();

        // On récupère toutes les vues dont on a besoin
        Button partager = (Button) findViewById(R.id.Button_partager);
        partager.setOnClickListener(new OnClickListener() {
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

        Button mUploadBtn = (Button) findViewById(R.id.Bupload);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mProgress = new ProgressDialog(this);

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("REPONDS", "Réponds peut être?");

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Log.e("DATA", "Intent data is different from null -> " + data);
                mProgress.setMessage("Upload en cours ...");
                mProgress.show();

                Bitmap image = (Bitmap) data.getExtras().get("data");

                mImageView.setImageBitmap(image);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                TextView mListePhoto = (TextView) findViewById(R.id.Liste_des_photos);

                StorageReference filepath = mStorage.child("Photos");
                mListePhoto.setText(filepath.toString());
                Log.e("WUUUUUUUUUUUUUUUUUUUUUU", filepath.toString());

                filepath.putBytes(byteArray)
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
                        Toast.makeText(MainActivity.this, "Ayayay\n", Toast.LENGTH_LONG).show();

                        Log.d("Tristitude", e.getMessage());
                    }
                });

            } else {
                Log.e("NO DATA", "Intent data equals null");
            }
        }

    }

}
