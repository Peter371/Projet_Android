package com.cours_openclassroom.pierre.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.view.View.OnClickListener;

/**
 * Created by Pierre on 10/06/2017.
 */

public class informations_photo extends Activity{
    private Button retour;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations_photo);

        retour = (Button) findViewById(R.id.retour);
        retour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent secondeActivite = new Intent(informations_photo.this, MainActivity.class);

                // Puis on lance l'intent !
                startActivity(secondeActivite);
                //Log.d("test", "------------------------------------------------------------------------------------------------------------------------------ ");

            }
        });

    }

}
