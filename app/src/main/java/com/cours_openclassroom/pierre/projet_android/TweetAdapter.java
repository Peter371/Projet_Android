package com.cours_openclassroom.pierre.projet_android;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Thomas on 12/06/2017.
 */

class TweetAdapter extends ArrayAdapter<Tweet> {
    Context context;
    private int layoutResId;
    ArrayList<Tweet> tweets;
    //tweets est la liste des models à afficher
    TweetAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, 0, tweets);
        this.context = context;
        this.tweets = tweets;

        Log.v("LstAdapter","Inside LstAdapter");
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return tweets.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            TweetViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.main, parent, false);
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TweetViewHolder) convertView.getTag();
        }

        Tweet tweet = getItem(position);
        assert tweet != null;
        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(tweet.getPseudo());
        viewHolder.text.setText(tweet.getText());
        Picasso.with(this.context).load(tweet.getDownLoadURI()).into(viewHolder.avatar);

        return convertView;
    }

    private class TweetViewHolder {
        TextView pseudo;
        TextView text;
        ImageView avatar;

    }
}
