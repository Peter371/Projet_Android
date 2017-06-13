package com.cours_openclassroom.pierre.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    private ArrayList<photoInformation> images;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference("linkDLphoto/");
    ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        mListView = (ListView) findViewById(R.id.photoView);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
        //android.R.layout.simple_list_item_1, prenoms);
        images = new ArrayList<>();
        ArrayList<Tweet> tweets = genererTweets();
        TweetAdapter adapter = new TweetAdapter(MainActivity.this, tweets);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Button partager = (Button) findViewById(R.id.Button_partager);
        partager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajouterActivite = new Intent(MainActivity.this, photo_partager.class);
                startActivity(ajouterActivite);
            }
        });

        Button mUploadBtn = (Button) findViewById(R.id.Bupload);
        //mImageView = (ImageView) findViewById(R.id.imageView);
        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent afficherActivite = new Intent(MainActivity.this, informations_photo.class);
                startActivity(afficherActivite);
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    photoInformation pi = postSnapshot.getValue(photoInformation.class);
                    Log.e("LOOK AT ME PIERRE", "Lolo? " + i + "\n- " + pi.getDownloadURI()
                            + "\n- " + pi.toString());
                    images.add(pi);
                    i++;
//                    Picasso.with(MainActivity.this)
//                            .load(Uri.parse(pi.getDownloadURI()))
//                            .into(mImageView);
                }
                //genererTweets();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        //genererTweets();
    }

    private ArrayList<Tweet> genererTweets() {
        ArrayList<Tweet> tweets = new ArrayList<>();
        int i = 0;
        for (photoInformation image : images) {
            i++;
//                URL urlPhoto = new URL(image.getDownloadURI());

//                Bitmap bmp = BitmapFactory.decodeStream(urlPhoto.openStream());
//                    Picasso.with(MainActivity.this)
//                            .load(Uri.parse(pi.getDownloadURI()))
//                            .into(mImageView);
//                Log.e("Amalou", urlPhoto.toString());
            //Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), urlPhoto);
            tweets.add(new Tweet(image.getNomPhoto(), "date: " + image.getDate() + "\npoids: " + image.getpoids() + " octets", image.getDownloadURI()));
            Log.e("BOOOOOOOOOOOOOOOOOOOO", "Yo "+i+") "+image.getDownloadURI());
            Log.i("Humhum", "taille de \"tweets\" : "+tweets.size());

        }
//        tweets.add(new Tweet(, "Florent", "Mon premier tweet !"));
//        tweets.add(new Tweet(Color.BLUE, "Kevin", "C'est ici que ça se passe !"));
//        tweets.add(new Tweet(Color.GREEN, "Logan", "Que c'est beau..."));
//        tweets.add(new Tweet(Color.RED, "Mathieu", "Il est quelle heure ??"));
//        tweets.add(new Tweet(Color.GRAY, "Willy", "On y est presque"));
        return tweets;
    }
    //convertView est notre vue recyclée

    public View getView(int position, View convertView, ViewGroup parent) {

        //Android nous fournit un convertView null lorsqu'il nous demande de la créer
        //dans le cas contraire, cela veux dire qu'il nous fournit une vue recyclée
        if (convertView == null) {
            //Nous récupérons notre row_tweet via un LayoutInflater,
            //qui va charger un layout xml dans un objet View
            convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.main, parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //nous renvoyons notre vue à l'adapter, afin qu'il l'affiche
        //et qu'il puisse la mettre à recycler lorsqu'elle sera sortie de l'écran
        return convertView;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
