package com.cours_openclassroom.pierre.projet_android;

import android.graphics.Bitmap;

/**
 * Created by Thomas on 12/06/2017.
 */

public class photoInformation {
    private String downloadURI;
    private String nomPhoto;
    private String date;
    private String poids;

    photoInformation(String downloadURI, String nomPhoto, String date, String poids) {
        this.downloadURI = downloadURI;
        this.nomPhoto = nomPhoto;
        this.date = date;
        this.poids = poids;
    }

    public photoInformation() {

    }

    public String getDownloadURI() {
        return downloadURI;
    }

    public String getNomPhoto() {
        return nomPhoto;
    }

    public String getDate() {
        return date;
    }

    public String getpoids() {
        return poids;
    }
}
