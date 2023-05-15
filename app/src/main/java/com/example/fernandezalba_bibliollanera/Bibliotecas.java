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
import android.widget.Toast;

import java.util.ArrayList;

public class Bibliotecas extends AppCompatActivity {
    //instancia para definir las acciones de la bbdd que se debe poner en todas las activitys
    private BBDD_Helper helper = new BBDD_Helper(this);
    private ArrayList<Biblioteca> listaBibliotecas;
    private RecyclerView rvBibliotecas;
    private String idUsuario="", admin="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotecas);

        //sacamos el dato de idUsuario
        Bundle b = getIntent().getExtras();
        idUsuario = b.getString("idUsuario");

        Bundle a = getIntent().getExtras();
        admin = a.getString("admin");

        insertaBiblios();

        rvBibliotecas = findViewById(R.id.rvBibliotecas);

        listaBibliotecas = new ArrayList<Biblioteca>();

        // Establecer layout del RecyclerView
        rvBibliotecas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
       //crear adaptador con ArrayLIst
        AdapterDatos_Biblioteca adapter = new AdapterDatos_Biblioteca(listaBibliotecas);
        //asignar al RecyclerView el adaptador
        rvBibliotecas.setAdapter(adapter);

        cargar();


    }


    //metodo que busca las bibliotecas existentes en la bbdd
    public void cargar (){

        //borramos los datos con los que llenamos el reclucer para que no se sobreescriban
        if (listaBibliotecas.size()>0) listaBibliotecas.clear();

        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Biblioteca.DIRECCION_BIBLIOTECA,
                Estructura_BBDD_Biblioteca.HORARIO_BIBLIOTECA,
                Estructura_BBDD_Biblioteca.TELEFONO_BIBLIOTECA,
        };

        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Biblioteca.TABLA_BIBLIOTECA,
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
                Biblioteca biblioteca = new Biblioteca(c.getString( 0),c.getString( 1), c.getString( 2));
                //añadimos ese objeto a un ArrayList
                listaBibliotecas.add(biblioteca);

            }while(c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor

            //aladimos el array a un adapter y ese adapter al ReciclerView
            AdapterDatos_Biblioteca adapter = new AdapterDatos_Biblioteca(listaBibliotecas);
            rvBibliotecas.setAdapter(adapter);

            //si no encuentra ningún libro en la bbdd salta por excepcion
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No existen bibliotecas", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    //metodo que comprueba si en la bbdd hay alguna biblio existente
    private boolean compruebaBiblio(){

        boolean hay = false;
        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Biblioteca.ID_BIBLIOTECA,

        };
        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Biblioteca.TABLA_BIBLIOTECA,
                    projection,
                    null,//esto es el where
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                //si hay algun id que sea 1, quiere decir que si hay biblios
                if(c.getString(0).equals("1") ) hay = true;

            }while(c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve false y sale del grupo
            c.close();//cerrar el cursor

        } catch (Exception e) {
            //si no consigue entrar en el cursor es porque no hay registros asi que no hay biblios, se pone hay a false
            hay = false;
        }
        //devolvemos si hay o no
        return hay;
    }

    //si no hay libros inserta los registros
    private void insertaBiblios(){
        //si se comprueban los libros y no hay ninguno
        if (!compruebaBiblio()){

            //insertamos los libros
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values1 = new ContentValues();
            values1.put(Estructura_BBDD_Biblioteca.DIRECCION_BIBLIOTECA, "Lugar Pondal 5, Lugo de Llanera");
            values1.put(Estructura_BBDD_Biblioteca.HORARIO_BIBLIOTECA, "11-14h y 16-20h");
            values1.put(Estructura_BBDD_Biblioteca.TELEFONO_BIBLIOTECA, "985771042");


            ContentValues values2 = new ContentValues();
            values2.put(Estructura_BBDD_Biblioteca.DIRECCION_BIBLIOTECA, "Av. Prudencio Gonzalez 10, Posada de Llanera");
            values2.put(Estructura_BBDD_Biblioteca.HORARIO_BIBLIOTECA, "10-14h y 16-22h");
            values2.put(Estructura_BBDD_Biblioteca.TELEFONO_BIBLIOTECA, "985773490");

            db.insert(Estructura_BBDD_Biblioteca.TABLA_BIBLIOTECA, null, values1);
            db.insert(Estructura_BBDD_Biblioteca.TABLA_BIBLIOTECA, null, values2);

            Toast.makeText(getApplicationContext(), "Se han insertado bibliotecas en la aplicación", Toast.LENGTH_LONG).show();
            db.close();

        }
    }
    //METODOS DEL MENU

    //crear el menu
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