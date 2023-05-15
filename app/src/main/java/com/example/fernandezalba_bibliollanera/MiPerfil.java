package com.example.fernandezalba_bibliollanera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MiPerfil extends AppCompatActivity {

    private String idUsuario = "";
    private EditText etNombrePerfil;
    private TextView tvLibroReservado;

    private String admin;

    //instancia para definir las acciones de la bbdd que se debe poner en todas las activitys
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        //sacamos el dato de idUsuario
        Bundle b = getIntent().getExtras();
        idUsuario = b.getString("idUsuario");

        Bundle a = getIntent().getExtras();
        admin = a.getString("admin");

        etNombrePerfil = findViewById(R.id.etNombrePerfil);
        tvLibroReservado = findViewById(R.id.tvLibroReservado);

        buscarReservado();
        buscarNombre();

    }

    //metodo que busca el libro que tenemos reservado para mostrarlo
    private void buscarReservado() {
        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();
        //los campos que devuelve la consulta

        String[] projection = {
                Estructura_BBDD_Usuario.ID_USUSARIO,
                Estructura_BBDD_Usuario.LIBRO_USUARIO,
        };
        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Usuario.TABLA_USUSARIO,
                    projection,
                    null,//esto es el where
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                if (c.getString(0).equals(idUsuario)) {
                    //se asigna al textView el libro
                    tvLibroReservado.setText(c.getString(1));
                }

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    private void buscarNombre() {
        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();
        //los campos que devuelve la consulta

        String[] projection = {
                Estructura_BBDD_Usuario.ID_USUSARIO,
                Estructura_BBDD_Usuario.NOMBRE_USUSARIO,
        };
        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Usuario.TABLA_USUSARIO,
                    projection,
                    null,//esto es el where
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                if (c.getString(0).equals(idUsuario)) {
                    //se asigna al textView el libro
                    etNombrePerfil.setText(c.getString(1));
                }
            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    //metodo que actualiza el nombre del usuario en la bbdd
    public void actualizaNombre(View vista) {

        String nombre = etNombrePerfil.getText().toString();

        if (!nombre.equals("")) { //si el nombre no esta vacio
            //me conecto en modo escritura
            SQLiteDatabase db = helper.getWritableDatabase();

            //se guarda en values el dato del nombre del usuario
            ContentValues values = new ContentValues();
            values.put(Estructura_BBDD_Usuario.NOMBRE_USUSARIO, nombre);

            String selection = Estructura_BBDD_Usuario.ID_USUSARIO + " LIKE ?";
            String[] selectionArgs = {idUsuario};
            //instruccion que actualiza la tabla libro
            db.update(Estructura_BBDD_Usuario.TABLA_USUSARIO, values, selection, selectionArgs);
            Toast.makeText(getApplicationContext(), "Nombre actualizado", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getApplicationContext(), "El nombre no puede estar en blanco", Toast.LENGTH_LONG).show();
    }

    //METODOS DEL MENU
    //Crea el menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //responder a las opciones del menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.libros:
                abrirLibros(null);
                break;
            case R.id.bibliotecas:
                abrirBibliotecas(null);
                break;
            case R.id.noticias:
                abrirPrincipal(null);
                break;
            case R.id.miPerfil:
                abrirMiPerfil(null);
                break;
            case R.id.salir:
                salir(null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //METODOS QUE ABREN ACTIVITYS
    public void abrirLibros(View vista){
        Intent i = new Intent(this, Libros.class);
        Bundle b = new Bundle();
        Bundle a = new Bundle();
        b.putString("idUsuario", idUsuario);
        a.putString("admin", admin);
        i.putExtras(b);
        i.putExtras(a);
        startActivity(i);
    }

    public void abrirBibliotecas(View vista){

        Intent i = new Intent(this, Bibliotecas.class);
        Bundle b = new Bundle();
        Bundle a = new Bundle();
        b.putString("idUsuario", idUsuario);
        a.putString("admin", admin);
        i.putExtras(b);
        i.putExtras(a);
        startActivity(i);
    }

    public void abrirMiPerfil(View vista){
        if (admin.equals("Si")) {

            Intent i = new Intent(this, MiPerfilAdmin.class);
            Bundle b = new Bundle();
            Bundle a = new Bundle();
            b.putString("idUsuario", idUsuario);
            a.putString("admin", admin);
            i.putExtras(b);
            i.putExtras(a);
            startActivity(i);
        }else {
            Intent i = new Intent(this, MiPerfil.class);
            Bundle b = new Bundle();
            Bundle a = new Bundle();
            b.putString("idUsuario", idUsuario);
            a.putString("admin", admin);
            i.putExtras(b);
            i.putExtras(a);
            startActivity(i);
        }

    }

    //METODO SALIR
    public void salir(View vista){
        new AlertDialog.Builder(this)
                .setTitle("Salir")
                .setMessage("Desea realmente salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        abrirMain();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    public void abrirPrincipal(View vista){
        Intent i = new Intent(this, Principal.class);
        Bundle b = new Bundle();
        Bundle a = new Bundle();
        b.putString("idUsuario", idUsuario);
        a.putString("admin", admin);
        i.putExtras(b);
        i.putExtras(a);
        startActivity(i);
    }

    public void abrirMain(){
        Intent i = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        Bundle a = new Bundle();
        b.putString("idUsuario", idUsuario);
        a.putString("admin", admin);
        i.putExtras(b);
        i.putExtras(a);
        startActivity(i);
    }
}