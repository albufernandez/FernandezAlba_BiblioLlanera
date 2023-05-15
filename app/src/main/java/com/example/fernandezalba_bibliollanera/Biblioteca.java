package com.example.fernandezalba_bibliollanera;

public class Biblioteca {

    //constructor
    public Biblioteca(int idBiblio, String direccionBiblio, String horarioBiblio, String telefonoBiblio ){
        this.idBiblio = idBiblio;
        this.direccionBiblio=direccionBiblio;
        this.horarioBiblio=horarioBiblio;
        this.telefonoBiblio = telefonoBiblio;
    }

    public Biblioteca( String direccionBiblio, String horarioBiblio, String telefonoBiblio ){
        this.direccionBiblio=direccionBiblio;
        this.horarioBiblio=horarioBiblio;
        this.telefonoBiblio = telefonoBiblio;
    }

    //variables
    public String direccionBiblio, horarioBiblio, telefonoBiblio;
    public int idBiblio;


    public String getDireccionBiblio() {
        return direccionBiblio;
    }

    public void setDireccionBiblio(String direccionBiblio) {
        this.direccionBiblio = direccionBiblio;
    }

    public String getHorarioBiblio() {
        return horarioBiblio;
    }

    public void setHorarioBiblio(String horarioBiblio) {
        this.horarioBiblio = horarioBiblio;
    }

    public int getIdBiblio() {
        return idBiblio;
    }

    public void setIdBiblio(int idBiblio) {
        this.idBiblio = idBiblio;
    }

    public String getTelefonoBiblio() {
        return telefonoBiblio;
    }

    public void setTelefonoBiblio(String telefonoBiblio) {
        this.telefonoBiblio = telefonoBiblio;
    }
}
