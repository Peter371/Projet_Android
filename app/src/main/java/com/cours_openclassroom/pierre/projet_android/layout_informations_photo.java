package com.cours_openclassroom.pierre.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
/**
 * Created by Pierre on 10/06/2017.
 */

public class layout_informations_photo extends Activity{
    private Button retour;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informations_photo);

        retour = (Button) findViewById(R.id.retour);
        retour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent secondeActivite = new Intent(layout_informations_photo.this, MainActivity.class);

                // Puis on lance l'intent !
                startActivity(secondeActivite);
                //Log.d("test", "------------------------------------------------------------------------------------------------------------------------------ ");

            }
        });

    }

}
