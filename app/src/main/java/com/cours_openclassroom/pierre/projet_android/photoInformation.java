package com.cours_openclassroom.pierre.projet_android;

import android.graphics.Bitmap;

/**
 * Created by Thomas on 12/06/2017.
 */

public class photoInformation {
    private String downloadURI;

    photoInformation(String downloadURI){
        this.downloadURI = downloadURI;
    }

    public photoInformation(){

    }

    public String getDownloadURI(){
        return downloadURI;
    }
    public void setDownloadURI(String downloadURI){
        this.downloadURI = downloadURI;
    }
}
