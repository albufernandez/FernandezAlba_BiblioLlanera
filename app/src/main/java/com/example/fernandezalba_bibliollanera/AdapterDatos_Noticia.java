package com.example.fernandezalba_bibliollanera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos_Noticia extends RecyclerView.Adapter<AdapterDatos_Noticia.ViewHolderDatos> {
    // Declarar ArrayList
    private ArrayList<Noticia> listaNoticias;

    // Constructor AdapterDatos_Libro
    public AdapterDatos_Noticia(ArrayList<Noticia> listaNoticias) {
        this.listaNoticias = listaNoticias; // Asignar ArrayList recibido como parámetro
    }

    // Crear contenedor de datos relacionándolo con el item_list
    public AdapterDatos_Noticia.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noticia, null, false);
        return new AdapterDatos_Noticia.ViewHolderDatos(vista);
    }

    // Enlazar el contenedor con las distintas posiciones del ArrayList
    public void onBindViewHolder(AdapterDatos_Noticia.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaNoticias.get(position));
    }

    // Devolver el tamaño del ArrayList
    public int getItemCount() {
        return listaNoticias.size();
    }

    // Clase ViewHolderDatos
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        // Declarar TextView
        private TextView tvNumeroNoticia,tvTextoNoticia;


        // Constructor ViewHolderDatos
        public ViewHolderDatos(View itemView) {
            super(itemView);
            // Inicializar TextView
            tvTextoNoticia = itemView.findViewById(R.id.tvTextoNoticia);

        }

        // Asignar los datos a los componentes (en este caso solo hay 1)
        public void asignarDatos(Noticia noticia) {

            tvTextoNoticia.setText(noticia.getTextoNoticia());

        }
    }
}
