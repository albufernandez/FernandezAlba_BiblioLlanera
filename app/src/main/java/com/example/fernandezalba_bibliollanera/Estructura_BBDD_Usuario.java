package com.example.fernandezalba_bibliollanera;

public class Estructura_BBDD_Usuario {

    // TABLA USUARIO
    public static final String TABLA_USUSARIO = "Usuarios";
    // Campos tabla
    public static final String ID_USUSARIO = "IdUsuario";
    public static final String NOMBRE_USUSARIO = "NombreUsuario";
    public static final String CONTRASEÑA_USUARIO = "ContraseñaUsuario";
    public static final String LIBRO_USUARIO = "LibroUsuario";
    public static final String TRABAJADOR_USUARIO = "TrabajadorUsuario";
    // Crear tabla
    public static final String SQL_CREATE_ENTRIES_USUARIO =
            "CREATE TABLE " + Estructura_BBDD_Usuario.TABLA_USUSARIO + " (" +
                    Estructura_BBDD_Usuario.ID_USUSARIO + " TEXT PRIMARY KEY," +
                    Estructura_BBDD_Usuario.NOMBRE_USUSARIO + " TEXT" + "," +
                    Estructura_BBDD_Usuario.CONTRASEÑA_USUARIO + " TEXT" + "," +
                    Estructura_BBDD_Usuario.LIBRO_USUARIO + " INTEGER" + "," +
                    Estructura_BBDD_Usuario.TRABAJADOR_USUARIO + " INTEGER" + ");";
    // Borrar tabla
    public static final String SQL_DELETE_ENTRIES_USUARIO =
            "DROP TABLE IF EXISTS " + Estructura_BBDD_Usuario.TABLA_USUSARIO;

}
