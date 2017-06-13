package com.cours_openclassroom.pierre.projet_android;

/**
 * Created by Thomas on 12/06/2017.
 */

public class Tweet {
    //private Bitmap img;
    private String pseudo;
    private String text;
    private String downLoadURI;

    Tweet(String pseudo, String text, String downloadURI) {
//        this.img = img;
        this.pseudo = pseudo;
        this.text = text;
        this.downLoadURI = downloadURI;
    }

//    Bitmap getimg() {
//        return img;
//    }
//
//    public void setimg(Bitmap img) {
//        this.img = img;
//    }

    String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDownLoadURI() {
        return downLoadURI;
    }
}
