package com.example.fernandezalba_bibliollanera;

public class Estructura_BBDD_Libro {
    // TABLA LIBROS
    public static final String TABLA_LIBRO = "Libros";
    //Campos tabla
    public static final String ID_LIBRO = "IdLibro";
    public static final String NOMBRE_LIBRO = "NombreLibro";
    public static final String AUTOR_LIBRO = "AutorLibro";
    public static final String GENERO_LIBRO = "GeneroLibro";
    public static final String BIBLIOTECA_LIBRO = "BibliotecaLibro";
    public static final String DISPONIBLE_LIBRO = "DisponibleLibro";
    // Crear tabla
    public static final String SQL_CREATE_ENTRIES_LIBRO =
            "CREATE TABLE " + Estructura_BBDD_Libro.TABLA_LIBRO + " (" +
                    Estructura_BBDD_Libro.ID_LIBRO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD_Libro.NOMBRE_LIBRO + " TEXT" + "," +
                    Estructura_BBDD_Libro.AUTOR_LIBRO + " TEXT" + "," +
                    Estructura_BBDD_Libro.GENERO_LIBRO + " TEXT" + "," +
                    Estructura_BBDD_Libro.BIBLIOTECA_LIBRO + " TEXT" + "," +
                    Estructura_BBDD_Libro.DISPONIBLE_LIBRO + " TEXT" + ");";
    // Borrar tabla
    public static final String SQL_DELETE_ENTRIES_LIBRO =
            "DROP TABLE IF EXISTS " + Estructura_BBDD_Libro.TABLA_LIBRO;
}
