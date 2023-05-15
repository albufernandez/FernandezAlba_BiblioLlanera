package com.example.fernandezalba_bibliollanera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    TextView tvNombre;
    String idUsuario="";
    String admin="";

    private ArrayList<Noticia> listaNoticias;

    private AdapterDatos_Noticia adapter;

    private RecyclerView rvNoticias;

    //instancia para definir las acciones de la bbdd que se debe poner en todas las activitys
    private BBDD_Helper helper = new BBDD_Helper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //sacamos el dato de idUsuario
        Bundle b = getIntent().getExtras();
        idUsuario = b.getString("idUsuario");

        Bundle a = getIntent().getExtras();
        admin = a.getString("admin");

        tvNombre= findViewById(R.id.tvNombre);
        tvNombre.setText(idUsuario);

        insertaLibros();

        insertaNoticias();

        compruebaNombre();

        rvNoticias= findViewById(R.id.rvNoticias);

        listaNoticias = new ArrayList<Noticia>();

        // Establecer layout del RecyclerView
        rvNoticias.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Crear adaptador pasándole como parámetro el ArrayList
        adapter = new AdapterDatos_Noticia(listaNoticias);

        // Asignar al RecyclerView el adaptador
        rvNoticias.setAdapter(adapter);

        cargarNoticias();

    }

    private boolean compruebaNoticias(){
        boolean hay = false;
        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Noticia.ID_NOTICIA,
        };

        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Noticia.TABLA_NOTICIA,
                    projection,
                    null ,//esto es el where para culumnas
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                if (c.getString(0).equals("1")) hay = true;
            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor
            db.close();

        } catch (Exception e) {
            //si no consigue entrar en el cursor es porque no hay registros asi que no hay libros, se pone hay a false
            hay = false;
        }
        //devolvemos si hay o no
        return hay;

    }

    private void insertaNoticias(){
        if (!compruebaNoticias()) {

            //insertamos las noticias
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values1 = new ContentValues();
            values1.put(Estructura_BBDD_Noticia.TEXTO_NOTICIA, "La biblioteca de Lugo de llanera renueva su imagen tras 2 meses cerrada. El centro de estudios anteriormente ubicado esta biblioteca se ha trasladado a C/Pelayo  n19 bajo. Las salas que previamente se usaban como sala de estudios ahora se usarán como salas de lectura para niños.");

            ContentValues values2 = new ContentValues();
            values2.put(Estructura_BBDD_Noticia.TEXTO_NOTICIA, "¡RECOGIDA DE LIBROS SOLIDARIA! La biblioteca de Posada está realizando una recogida solidaria de libros para l@s niñ@s del HUCA. Tienes hasta el 15 de Junio para poder donar tantos libros como desees.");

            db.insert(Estructura_BBDD_Noticia.TABLA_NOTICIA, null, values1);
            db.insert(Estructura_BBDD_Noticia.TABLA_NOTICIA, null, values2);

            Toast.makeText(getApplicationContext(), "Se han insertado noticias en la aplicación", Toast.LENGTH_LONG).show();
            db.close();

        }
    }

    public void cargarNoticias (){

        //borramos los datos con los que llenamos el reclucer para que no se sobreescriban
        if (listaNoticias.size()>0) listaNoticias.clear();

        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Noticia.TEXTO_NOTICIA,

        };

        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Noticia.TABLA_NOTICIA,
                    projection,
                    null,//esto es el where
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                //añadimos los campos que devuelve la consulta a un objeto libro
                Noticia noticia = new Noticia(c.getString(0));
                //añadimos ese objeto a un ArrayList
                listaNoticias.add(noticia);

            }while(c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor


            //aladimos el array a un adapter y ese adapter al ReciclerView
            AdapterDatos_Noticia adapter = new AdapterDatos_Noticia(listaNoticias);
            rvNoticias.setAdapter(adapter);

            //si no encuentra ningún libro en la bbdd salta por excepcion
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No existen noticias", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    //si no hay libros inserta los registros
    private void insertaLibros() {
        //si se comprueban los libros y no hay ninguno
        if (!compruebaLibros()) {

            //insertamos los libros
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values1 = new ContentValues();
            values1.put(Estructura_BBDD_Libro.NOMBRE_LIBRO, "Juego de tronos");
            values1.put(Estructura_BBDD_Libro.AUTOR_LIBRO, "George RR Martin");
            values1.put(Estructura_BBDD_Libro.GENERO_LIBRO, "Fantasia");
            values1.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "Si");
            values1.put(Estructura_BBDD_Libro.BIBLIOTECA_LIBRO, "01");

            ContentValues values2 = new ContentValues();
            values2.put(Estructura_BBDD_Libro.NOMBRE_LIBRO, "Las madres");
            values2.put(Estructura_BBDD_Libro.AUTOR_LIBRO, "Carmen Mola");
            values2.put(Estructura_BBDD_Libro.GENERO_LIBRO, "Policiaco");
            values2.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "Si");
            values2.put(Estructura_BBDD_Libro.BIBLIOTECA_LIBRO, "01");

            ContentValues values3 = new ContentValues();
            values3.put(Estructura_BBDD_Libro.NOMBRE_LIBRO, "Mi isla");
            values3.put(Estructura_BBDD_Libro.AUTOR_LIBRO, "Elisabet Benavent");
            values3.put(Estructura_BBDD_Libro.GENERO_LIBRO, "Romantico");
            values3.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "No");
            values3.put(Estructura_BBDD_Libro.BIBLIOTECA_LIBRO, "01");

            ContentValues values4 = new ContentValues();
            values4.put(Estructura_BBDD_Libro.NOMBRE_LIBRO, "It");
            values4.put(Estructura_BBDD_Libro.AUTOR_LIBRO, "Stephen King");
            values4.put(Estructura_BBDD_Libro.GENERO_LIBRO, "Terror");
            values4.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "Si");
            values4.put(Estructura_BBDD_Libro.BIBLIOTECA_LIBRO, "01");

            ContentValues values5 = new ContentValues();
            values5.put(Estructura_BBDD_Libro.NOMBRE_LIBRO, "La chica del tren");
            values5.put(Estructura_BBDD_Libro.AUTOR_LIBRO, "Paula Hawkings");
            values5.put(Estructura_BBDD_Libro.GENERO_LIBRO, "Policiaco");
            values5.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "No");
            values5.put(Estructura_BBDD_Libro.BIBLIOTECA_LIBRO, "02");

            ContentValues values6 = new ContentValues();
            values6.put(Estructura_BBDD_Libro.NOMBRE_LIBRO, "Carter & Lovecraft");
            values6.put(Estructura_BBDD_Libro.AUTOR_LIBRO, "Jonathan L. Howard");
            values6.put(Estructura_BBDD_Libro.GENERO_LIBRO, "Fantasia");
            values6.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "Si");
            values6.put(Estructura_BBDD_Libro.BIBLIOTECA_LIBRO, "02");

            ContentValues values7 = new ContentValues();
            values7.put(Estructura_BBDD_Libro.NOMBRE_LIBRO, "Dracula");
            values7.put(Estructura_BBDD_Libro.AUTOR_LIBRO, "Bram Stoker");
            values7.put(Estructura_BBDD_Libro.GENERO_LIBRO, "Terror");
            values7.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "Si");
            values7.put(Estructura_BBDD_Libro.BIBLIOTECA_LIBRO, "02");

            db.insert(Estructura_BBDD_Libro.TABLA_LIBRO, null, values1);
            db.insert(Estructura_BBDD_Libro.TABLA_LIBRO, null, values2);
            db.insert(Estructura_BBDD_Libro.TABLA_LIBRO, null, values3);
            db.insert(Estructura_BBDD_Libro.TABLA_LIBRO, null, values4);
            db.insert(Estructura_BBDD_Libro.TABLA_LIBRO, null, values5);
            db.insert(Estructura_BBDD_Libro.TABLA_LIBRO, null, values6);
            db.insert(Estructura_BBDD_Libro.TABLA_LIBRO, null, values7);

            Toast.makeText(getApplicationContext(), "Se han insertado libros en la aplicación", Toast.LENGTH_LONG).show();
            db.close();

        }
    }
    //metodo que comprueba si en la bbdd hay algún libro existente
    private boolean compruebaLibros() {
        boolean hay = false;
        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Libro.ID_LIBRO,
        };
        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Libro.TABLA_LIBRO,
                    projection,
                    null,//esto es el where
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                //si hay algun id que sea 1, quiere decir que si hay libros
                if (c.getString(0).equals("1")) hay = true;

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve false y sale del grupo
            c.close();//cerrar el cursor

        } catch (Exception e) {
            //si no consigue entrar en el cursor es porque no hay registros asi que no hay libros, se pone hay a false
            hay = false;
        }
        //devolvemos si hay o no
        return hay;
    }

    private void compruebaNombre() {
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
                //si hay algun id que se encuentra es igual al pasado desde la main se asiga el nombre
                if (c.getString(0).equals(idUsuario)) tvNombre.setText(c.getString(1));

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve false y sale del grupo
            c.close();//cerrar el cursor

        } catch (Exception e) {

        }
    }

    //METODOS DEL MENU
    //Crea el menu
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //responder a las opciones del menu
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
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