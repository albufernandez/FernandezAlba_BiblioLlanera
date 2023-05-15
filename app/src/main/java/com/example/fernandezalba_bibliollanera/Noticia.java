package com.example.fernandezalba_bibliollanera;

public class Noticia {

    //constructor
    public Noticia(int idNoticia, String textoNoticia){
        this.idNoticia = idNoticia;
        this.textoNoticia = textoNoticia;
    }

    public Noticia(String textoNoticia){
        this.textoNoticia = textoNoticia;
    }

    //variables
    public int idNoticia;
    public String textoNoticia;

    //getters y setters
    public int getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(int idNoticia) {
        this.idNoticia = idNoticia;
    }

    public String getTextoNoticia() {
        return textoNoticia;
    }

    public void setTextoNoticia(String textoNoticia) {
        this.textoNoticia = textoNoticia;
    }
}
