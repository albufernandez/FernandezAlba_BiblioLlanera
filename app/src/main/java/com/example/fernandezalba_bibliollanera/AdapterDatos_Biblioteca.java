package com.example.fernandezalba_bibliollanera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos_Biblioteca extends RecyclerView.Adapter<AdapterDatos_Biblioteca.ViewHolderDatos> {
    // Declarar ArrayList
    private ArrayList<Biblioteca> listaBibliotecas;

    // Constructor AdapterDatos_Libro
    public AdapterDatos_Biblioteca(ArrayList<Biblioteca> listaBibliotecas) {
        this.listaBibliotecas = listaBibliotecas; // Asignar ArrayList recibido como parámetro
    }

    // Crear contenedor de datos relacionándolo con el item_list
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.biblioteca, null, false);
        return new AdapterDatos_Biblioteca.ViewHolderDatos(vista);
    }

    // Enlazar el contenedor con las distintas posiciones del ArrayList
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaBibliotecas.get(position));
    }

    // Devolver el tamaño del ArrayList
    public int getItemCount() {
        return listaBibliotecas.size();
    }

    // Clase ViewHolderDatos
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        // Declarar TextView
        private TextView tvDireccion;
        private TextView tvHorario;
        private TextView tvTelefono;


        // Si hay más campos, declararlos todos

        // Constructor ViewHolderDatos
        public ViewHolderDatos(View itemView) {
            super(itemView);
            // Inicializar TextView
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvHorario = itemView.findViewById(R.id.tvHorario);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);

        }

        // Asignar los datos a los componentes (en este caso solo hay 1)
        public void asignarDatos(Biblioteca libro) {

            tvDireccion.setText(libro.getDireccionBiblio());
            tvHorario.setText(libro.getHorarioBiblio());
            tvTelefono.setText(libro.getTelefonoBiblio());

        }
    }
}
