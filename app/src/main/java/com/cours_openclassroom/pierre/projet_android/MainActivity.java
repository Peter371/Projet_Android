package com.cours_openclassroom.pierre.projet_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends Activity {
    private static final int CAMERA_REQUEST_CODE = 1;
    private StorageReference mStorage;
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
                Intent secondeActivite = new Intent(MainActivity.this, informations_photo.class);

                // Puis on lance l'intent !
                startActivity(secondeActivite);
                //Log.d("test", "------------------------------------------------------------------------------------------------------------------------------ ");

            }
        });

        Button mUploadBtn = (Button) findViewById(R.id.Bupload);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent afficherActivite = new Intent(MainActivity.this, photo_partager.class);
                startActivity(afficherActivite);
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
