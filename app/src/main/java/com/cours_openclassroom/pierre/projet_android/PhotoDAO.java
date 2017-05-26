package com.cours_openclassroom.pierre.projet_android;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by Pierre on 25/05/2017.
 */
public class PhotoDAO extends DAOBase {
    public static final String Id_photo = "Id_photo";
    public static final String Nom_photo = "Nom";
    public static final String Taille_photo = "Taille";
    public static final String Lieu_photo = "Lieu";
    public static final String Date_photo = "Date";

    public static final String Nom_table_photo = "Photo";
    public static final String Requete_SQL_creation_table_photo =
                    "CREATE TABLE " + Nom_table_photo + " (" +
                    Id_photo + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Nom_photo + " TEXT, " +
                    Taille_photo + " INTEGER" +
                    Lieu_photo + " TEXT " +
                    Date_photo + "  TEXT " +
                    ");";

    public static final String Requete_SQL_suppression_table_photo = "DROP TABLE IF EXISTS " + Nom_table_photo + ";";

    public PhotoDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param photo la photo à ajouter à la base
     */
    public void ajouter(Photo photo) {
        ContentValues value = new ContentValues();
        value.put(PhotoDAO.Nom_photo, photo.getNom());
        value.put(PhotoDAO.Taille_photo, photo.getTaille());
        value.put(PhotoDAO.Lieu_photo, photo.getLieu());
        value.put(PhotoDAO.Date_photo, photo.getLieu());
        mDb.insert(PhotoDAO.Nom_table_photo, null, value);

    }

    /**
     * @param id_photo l'identifiant de la photo à supprimer
     */
    public void supprimer(long id_photo) {
        // CODE
    }

    /**
     * @param photo la Photo modifié
     */
    public void modifier(Photo photo) {
        // CODE
    }

    /**
     * @param id_photo l'identifiant de la photo à récupérer
     */
    /*public Photo selectionner(long id_photo) {
        // CODE
    }*/
}
