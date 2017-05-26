package com.cours_openclassroom.pierre.projet_android;

/**
 * Created by Pierre on 25/05/2017.
 */

public class Photo {

    // Notez que l'identifiant est un long
    private long Id_photo;
    private String Nom;
    private Integer Taille;
    private String Lieu;
    private String Date;

    public Photo(long Id_photo, String Nom, Integer Taille, String Lieu, String Date) {
        super();
        this.Id_photo = Id_photo;
        this.Nom = Nom;
        this.Taille = Taille;
        this.Lieu = Lieu;
        this.Date = Date;
    }

    public long getId() {
        return Id_photo;
    }

    public void setId(long id) {
        this.Id_photo = Id_photo;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public Integer getTaille() {
        return Taille;
    }

    public void setTaille(Integer Taille) {
        this.Taille = Taille;
    }

    public String getLieu() {
        return Lieu;
    }

    public void setLieu(String Lieu) {
        this.Lieu = Lieu;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

}
