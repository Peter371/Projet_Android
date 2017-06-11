package com.cours_openclassroom.pierre.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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

        Button partager = (Button) findViewById(R.id.Button_partager);
        partager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajouterActivite = new Intent(MainActivity.this, photo_partager.class);
                startActivity(ajouterActivite);
            }
        });

        Button mUploadBtn = (Button) findViewById(R.id.Bupload);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent afficherActivite = new Intent(MainActivity.this, informations_photo.class);
                startActivity(afficherActivite);
            }
        });


        StorageReference filepath = mStorage.child("Photos");
        TextView tvListPhoto = (TextView) findViewById(R.id.Liste_des_photos);
        tvListPhoto.setText(filepath.toString());
        Picasso.with(this)
                .load(filepath.getPath())
                .into(mImageView);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
