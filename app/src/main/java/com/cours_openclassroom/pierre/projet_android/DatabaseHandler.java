package com.cours_openclassroom.pierre.projet_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pierre on 25/05/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
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

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Requete_SQL_creation_table_photo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Requete_SQL_suppression_table_photo);
        onCreate(db);
    }


}
