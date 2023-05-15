package com.example.fernandezalba_bibliollanera;

public class Estructura_BBDD_Biblioteca {

    //TABLA BIBLIOTECAS
    public static final String TABLA_BIBLIOTECA = "Biblioteca";
    // Campos tabla
    public static final String ID_BIBLIOTECA = "IdBiblioteca";
    public static final String DIRECCION_BIBLIOTECA = "DireccionBiblioteca";
    public static final String HORARIO_BIBLIOTECA = "HorarioBiblioteca";
    public static final String TELEFONO_BIBLIOTECA = "TelefonoBiblioteca";

    // Crear tabla
    public static final String SQL_CREATE_ENTRIES_BIBLIOTECA =
            "CREATE TABLE " + Estructura_BBDD_Biblioteca.TABLA_BIBLIOTECA + " (" +
                    Estructura_BBDD_Biblioteca.ID_BIBLIOTECA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD_Biblioteca.DIRECCION_BIBLIOTECA + " TEXT" + "," +
                    Estructura_BBDD_Biblioteca.HORARIO_BIBLIOTECA + " TEXT" + "," +
                    Estructura_BBDD_Biblioteca.TELEFONO_BIBLIOTECA + " TEXT" + ");";
    // Borrar tabla
    public static final String SQL_DELETE_ENTRIES_BIBLIOTECA =
            "DROP TABLE IF EXISTS " + Estructura_BBDD_Biblioteca.TABLA_BIBLIOTECA;
}
