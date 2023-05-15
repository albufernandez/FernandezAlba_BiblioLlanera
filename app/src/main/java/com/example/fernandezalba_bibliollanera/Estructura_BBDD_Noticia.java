package com.example.fernandezalba_bibliollanera;

public class Estructura_BBDD_Noticia {

    public static final String TABLA_NOTICIA = "Noticia";

    public static final String ID_NOTICIA = "IdNoticia";
    public static final String TEXTO_NOTICIA = "TextoNoticia";

    public static final String SQL_CREATE_ENTRIES_NOTICIA =
            "CREATE TABLE " + Estructura_BBDD_Noticia.TABLA_NOTICIA + " (" +
                    Estructura_BBDD_Noticia.ID_NOTICIA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD_Noticia.TEXTO_NOTICIA + " TEXT" + ");";
    // Borrar tabla
    public static final String SQL_DELETE_ENTRIES_NOTICIA =
            "DROP TABLE IF EXISTS " + Estructura_BBDD_Noticia.TABLA_NOTICIA;
}
