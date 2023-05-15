package com.example.fernandezalba_bibliollanera;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BBDD_Helper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1; // Versión BD
    public static final String DATABASE_NAME = "bibliollanera.db"; // Nombre BD

    // Constructor BBDD_Helper
    public BBDD_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Crear BD
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Estructura_BBDD_Libro.SQL_CREATE_ENTRIES_LIBRO);
        db.execSQL(Estructura_BBDD_Usuario.SQL_CREATE_ENTRIES_USUARIO);
        db.execSQL(Estructura_BBDD_Biblioteca.SQL_CREATE_ENTRIES_BIBLIOTECA);
        db.execSQL(Estructura_BBDD_Noticia.SQL_CREATE_ENTRIES_NOTICIA);
    }

    // Actualizar BD (si se actualiza la estructura)
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Estructura_BBDD_Libro.SQL_DELETE_ENTRIES_LIBRO);
        db.execSQL(Estructura_BBDD_Usuario.SQL_DELETE_ENTRIES_USUARIO);
        db.execSQL(Estructura_BBDD_Biblioteca.SQL_DELETE_ENTRIES_BIBLIOTECA);
        db.execSQL(Estructura_BBDD_Noticia.SQL_DELETE_ENTRIES_NOTICIA);
        onCreate(db);
    }

    // Cambiar versión BD
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
