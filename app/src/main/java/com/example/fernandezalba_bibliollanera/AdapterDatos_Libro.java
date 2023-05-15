package com.example.fernandezalba_bibliollanera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos_Libro extends RecyclerView.Adapter<AdapterDatos_Libro.ViewHolderDatos> {
    // Declarar ArrayList
    private ArrayList<Libro> listaLibros;

    // Constructor AdapterDatos_Libro
    public AdapterDatos_Libro(ArrayList<Libro> listaLibros) {
        this.listaLibros = listaLibros; // Asignar ArrayList recibido como parámetro
    }

    // Crear contenedor de datos relacionándolo con el item_list
    public AdapterDatos_Libro.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.libro, null, false);
        return new ViewHolderDatos(vista);
    }

    // Enlazar el contenedor con las distintas posiciones del ArrayList
    public void onBindViewHolder(AdapterDatos_Libro.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaLibros.get(position));
    }

    // Devolver el tamaño del ArrayList
    public int getItemCount() {
        return listaLibros.size();
    }

    // Clase ViewHolderDatos
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        // Declarar TextView
        private TextView tvNombre;
        private TextView tvAutor;
        private TextView tvGenero;
        private TextView tvDisponible;

        private TextView tvBiblioteca;

        // Si hay más campos, declararlos todos

        // Constructor ViewHolderDatos
        public ViewHolderDatos(View itemView) {
            super(itemView);
            // Inicializar TextView
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvGenero = itemView.findViewById(R.id.tvGenero);
            tvDisponible = itemView.findViewById(R.id.tvDisponible);
            tvBiblioteca = itemView.findViewById(R.id.tvBiblioteca);
        }

        // Asignar los datos a los componentes (en este caso solo hay 1)
        public void asignarDatos(Libro libro) {

            tvNombre.setText(libro.getNombreLibro());
            tvAutor.setText(libro.getAutorLibro());
            tvGenero.setText(libro.getGeneroLibro());
            tvDisponible.setText(libro.getDisponibleLibro());
            tvBiblioteca.setText(libro.getBibliotecaLibro());
        }
    }
}
