package com.example.fernandezalba_bibliollanera;

public class Usuario {
    //constructor
    public Usuario(String idUsuario, String nombreUsuario, String contraseñaUsuario, int biblioFav, String devolucion, int libro, int  trabajador ){
        this.idUsuario = idUsuario;
        this.nombreUsuario=nombreUsuario;
        this.contraseñaUsuario = contraseñaUsuario;
        this.biblioFav=biblioFav;
        this.devolucion = devolucion;
        this.libro = libro;
        this.trabajador = trabajador;

    }

    //variables
    public String idUsuario, nombreUsuario, contraseñaUsuario, devolucion;
    public int biblioFav, libro, trabajador;


    public String getContraseñaUsuario() {
        return contraseñaUsuario;
    }

    public void setContraseñaUsuario(String contraseñaUsuario) {
        this.contraseñaUsuario = contraseñaUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(String devolucion) {
        this.devolucion = devolucion;
    }

    public int getBiblioFav() {
        return biblioFav;
    }

    public void setBiblioFav(int biblioFav) {
        this.biblioFav = biblioFav;
    }

    public int getLibro() {
        return libro;
    }

    public void setLibro(int libro) {
        this.libro = libro;
    }

    public int getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(int trabajador) {
        this.trabajador = trabajador;
    }
}
