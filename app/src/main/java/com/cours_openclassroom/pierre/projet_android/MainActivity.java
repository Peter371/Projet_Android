package com.cours_openclassroom.pierre.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{
    Button Partager = null;

    TextView Liste_des_photos = null;
    TextView Titre_colonne_photo = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère toutes les vues dont on a besoin
        Partager = (Button)findViewById(R.id.Button_partager);
        Partager.setOnClickListener(partagerListener);
    }


    // Uniquement pour le bouton "Partager"
   private OnClickListener partagerListener = new OnClickListener() {
        @Override
        public void onClick(View v) {


            // Le premier paramètre est le nom de l'activité actuelle
            // Le second est le nom de l'activité de destination
            Intent secondeActivite = new Intent(MainActivity.this, choix_photo_partager.class);

            // Puis on lance l'intent !
            startActivity(secondeActivite);
            //Log.d("test", "------------------------------------------------------------------------------------------------------------------------------ ");

        }
    };

}
