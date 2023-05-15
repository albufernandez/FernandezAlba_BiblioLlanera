package com.example.fernandezalba_bibliollanera;

public class Libro {

    //constructor
    public Libro(int idLibro, String nombreLibro, String bibliotecaLibro, String autorLibro, String generoLibro, String  disponibleLibro ){
        this.idLibro = idLibro;
        this.nombreLibro=nombreLibro;
        this.bibliotecaLibro=bibliotecaLibro;
        this.autorLibro = autorLibro;
        this.generoLibro = generoLibro;
        this.disponibleLibro = disponibleLibro;

    }

    public Libro (String nombreLibro, String autorLibro, String generoLibro, String disponibleLibro, String bibliotecaLibro){
        this.nombreLibro = nombreLibro;
        this.autorLibro = autorLibro;
        this.generoLibro = generoLibro;
        this.disponibleLibro = disponibleLibro;
        this.bibliotecaLibro = bibliotecaLibro;
    }

    //variables
    public String nombreLibro, autorLibro, generoLibro,disponibleLibro, bibliotecaLibro;
    public int idLibro;


    //GETTERS Y SETTERS


    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getAutorLibro() {
        return autorLibro;
    }

    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }

    public String getGeneroLibro() {
        return generoLibro;
    }

    public void setGeneroLibro(String generoLibro) {
        this.generoLibro = generoLibro;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getDisponibleLibro() {
        return disponibleLibro;
    }

    public void setDisponibleLibro(String disponibleLibro) {
        this.disponibleLibro = disponibleLibro;
    }

    public String getBibliotecaLibro() {
        String tBiblioteca="";
        if(bibliotecaLibro.equals("01")){
            tBiblioteca="Lugo de Llanera";
        }
        else tBiblioteca="Posada de Llanera";
        return tBiblioteca;
    }

    public void setBibliotecaLibro(String bibliotecaLibro) {
        this.bibliotecaLibro = bibliotecaLibro;
    }
}
