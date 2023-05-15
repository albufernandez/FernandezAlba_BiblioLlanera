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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Libros extends AppCompatActivity {
    private ArrayList<Libro> listaLibros;
    private EditText etNombre, etAutor;
    private Spinner spGenero;
    private RecyclerView rvLibros;
    private CheckBox cbDisponible;
    private String idUsuario = "", admin = "";
    private RadioButton rbLugo, rbPosada;

    private AdapterDatos_Libro adapter;
    private int libros = 0;

    //instancia para definir las acciones de la bbdd que se debe poner en todas las activitys
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);

        //sacamos el dato de idUsuario
        Bundle b = getIntent().getExtras();
        idUsuario = b.getString("idUsuario");

        Bundle a = getIntent().getExtras();
        admin = a.getString("admin");

        insertaLibros();

        rvLibros = findViewById(R.id.rvLibros);
        etNombre = findViewById(R.id.etNombre);
        etAutor = findViewById(R.id.etAutor);
        spGenero = findViewById(R.id.spGenero);
        rbLugo = findViewById(R.id.rbLugo);
        rbPosada = findViewById(R.id.rbPosada);

        rvLibros = findViewById(R.id.rvLibros);
        cbDisponible = findViewById(R.id.cbDisponible);

        rbLugo.setChecked(true);

        // Establecer layout del RecyclerView
        rvLibros.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listaLibros = new ArrayList<Libro>();

        // Crear adaptador pasándole como parámetro el ArrayList
        adapter = new AdapterDatos_Libro(listaLibros);

        // Asignar al RecyclerView el adaptador
        rvLibros.setAdapter(adapter);

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

    //metodo que busca los libros existentes en la bbdd (HACER BUSQUEDA PERSONALIZADA)
    public void buscar(View vista) {

        String nombre = etNombre.getText().toString();
        String autor = etAutor.getText().toString();

        String genero = compruebGenero(spGenero);
        String generoCon="";
        if(genero.equals("Todos")) {
            generoCon = "%%"; //coge cualquier string que contenga una letra de la a a la z y con lo que sea por delante y por detras
        }else generoCon=genero;


        String biblio = "";

        if (rbLugo.isChecked()) biblio = "01"; //biblio de lugo de llanera tiene id 1
        if (rbPosada.isChecked()) biblio = "02"; //tiene id 2


        String disponible;
        if (cbDisponible.isChecked()) disponible = "Si";
        else disponible = "%%";

        //borramos los datos con los que llenamos el reclucer para que no se sobreescriban
        if (listaLibros.size() > 0) {
            listaLibros.clear();
        }
        libros = 0;

        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Libro.NOMBRE_LIBRO,
                Estructura_BBDD_Libro.AUTOR_LIBRO,
                Estructura_BBDD_Libro.GENERO_LIBRO,
                Estructura_BBDD_Libro.DISPONIBLE_LIBRO,
                Estructura_BBDD_Libro.BIBLIOTECA_LIBRO
        };

        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Libro.TABLA_LIBRO,
                    projection,
                    Estructura_BBDD_Libro.NOMBRE_LIBRO + " LIKE '" + nombre +"%' AND " + Estructura_BBDD_Libro.AUTOR_LIBRO + " LIKE '" + autor + "%' AND "
                            + Estructura_BBDD_Libro.GENERO_LIBRO + " LIKE '" + generoCon + "' AND " + Estructura_BBDD_Libro.DISPONIBLE_LIBRO + " LIKE '" + disponible +
                    "' AND " + Estructura_BBDD_Libro.BIBLIOTECA_LIBRO + " LIKE '" + biblio +"'",//esto es el where para culumnas
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                    //añadimos los campos que devuelve la consulta a un objeto libro
                    Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                    //añadimos ese objeto a un ArrayList
                    listaLibros.add(libro);
                    libros++;

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor



            //si no encuentra ningún libro en la bbdd salta por excepcion
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No existen libros", Toast.LENGTH_LONG).show();

        }
        //añadimos el array a un adapter y ese adapter al ReciclerView
        AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
        rvLibros.setAdapter(adapter);
        db.close();

    }

    public void reservar(View vista) {
        //si la busqueda devuelve mas de un libro sale una alerta
        if (listaLibros.size() > 1) {
            Toast.makeText(this, "Solo se puede reservar un libro", Toast.LENGTH_SHORT).show();
        } else {
            Libro libro = listaLibros.get(0);
            String dispo = libro.getDisponibleLibro();
            //se comprueba que el usuario si pueda reservar
            if (compruebaReserva()) {
                //se comprueba si el libro se puede reservar
                if (dispo.equalsIgnoreCase("no")) {
                    Toast.makeText(this, "Este libro no se encuentra disponible", Toast.LENGTH_SHORT).show();
                } else {
                    String NomLibro = libro.getNombreLibro();
                    //me conecto en modo escritura
                    SQLiteDatabase db = helper.getWritableDatabase();

                    //Primero se actualiza la tabla libro
                    //se guarda en values el dato de que no esta el libro disponible
                    ContentValues values = new ContentValues();
                    values.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "No");

                    String selection = Estructura_BBDD_Libro.NOMBRE_LIBRO + " LIKE ?";
                    String[] selectionArgs = {NomLibro};
                    //instruccion que actualiza la tabla libro
                    db.update(Estructura_BBDD_Libro.TABLA_LIBRO, values, selection, selectionArgs);

                    //Ahora se actualiza la tabla usuario reservando el libro
                    ContentValues valuesU = new ContentValues();
                    valuesU.put(Estructura_BBDD_Usuario.LIBRO_USUARIO, NomLibro);

                    String selectionU = Estructura_BBDD_Usuario.ID_USUSARIO + " LIKE ?";
                    String[] selectionArgsU = {idUsuario};

                    //instruccion que actualiza la tabla usuario
                    db.update(Estructura_BBDD_Usuario.TABLA_USUSARIO, valuesU, selectionU, selectionArgsU);
                    Toast.makeText(this, "Libro reservado correctamente", Toast.LENGTH_SHORT).show();
                }
            } else Toast.makeText(this, "Ya tienes un libro reservado", Toast.LENGTH_SHORT).show();
        }

        // Crear adaptador pasándole como parámetro el ArrayList
        AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
        rvLibros.setAdapter(adapter);

        buscar(vista);

    }


    //metodo que comprueba el geneero seleccionado y lo devuelve en String
    public String compruebGenero(Spinner spGenero) {
        String genero = "";
        long sel = spGenero.getSelectedItemId();
        if (sel == 0) genero = "Todos";
        if (sel == 1) genero = "Fantasia";
        if (sel == 2) genero = "Policiaco";
        if (sel == 3) genero = "Romantico";
        if (sel == 4) genero = "Terror";
        return genero;
    }

    //metodo que devuelve true si el usuario no tiene libros reservados (devuelve si puede reservar)
    private boolean compruebaReserva() {
        boolean puede = true;
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
                String nombre = c.getString(1);
                if (idUsuario.equals(c.getString(0))) {
                    if (nombre.length() > 1) puede = false;
                }

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        }
        db.close();


        return puede;
    }

    //METODOS DEL MENU
    //crear el menu
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

/* public void buscar(View vista) {

        String nombre = etNombre.getText().toString();
        String autor = etAutor.getText().toString();

        String genero = compruebGenero(spGenero);

        String biblio = "";
        if (rbLugo.isChecked()) biblio = "Lugo de Llanera";
        if (rbPosada.isChecked()) biblio = "Posada de Llanera";

        String disponible;
        if (cbDisponible.isChecked()) disponible = "Si";
        else disponible = "No";

        //borramos los datos con los que llenamos el reclucer para que no se sobreescriban
        if (listaLibros.size() > 0) listaLibros.clear();
        libros = 0;

        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Libro.NOMBRE_LIBRO,
                Estructura_BBDD_Libro.AUTOR_LIBRO,
                Estructura_BBDD_Libro.GENERO_LIBRO,
                Estructura_BBDD_Libro.DISPONIBLE_LIBRO,
                Estructura_BBDD_Libro.BIBLIOTECA_LIBRO
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
                //en caso de que no se haya puesto NINGUN TIPO DE FILTRO saca todos los libros
                if (nombre.equals("") && autor.equals("") && genero.equals("Todos") && disponible.equals("No")) {
                    //añadimos los campos que devuelve la consulta a un objeto libro
                    Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                    //añadimos ese objeto a un ArrayList
                    listaLibros.add(libro);
                    libros++;
                }

                //en caso de que no se haya puesto NINGUN TIPO DE FILTRO SOLO DISPONIBLES
                if (nombre.equals("") && autor.equals("") && genero.equals("Todos") && disponible.equals("Si")) {
                    if (c.getString(3).equals("Si")) {
                        //añadimos los campos que devuelve la consulta a un objeto libro
                        Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                        //añadimos ese objeto a un ArrayList
                        listaLibros.add(libro);
                        libros++;
                    }
                }
                //ningun tipo de filtro excepto biblioteca
                if (nombre.equals("") && autor.equals("") && genero.equals("Todos") && disponible.equals("No") && biblio.equals("Posada de Llanera")) {
                    if (c.getString(4).equals("Posada de Llanera")) {
                        //añadimos los campos que devuelve la consulta a un objeto libro
                        Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                        //añadimos ese objeto a un ArrayList
                        listaLibros.add(libro);
                        libros++;
                    }
                }

                //SI SE HA PUESTO FILTRO A NOMBRE LIBRO
                else if (!nombre.equals("")) {
                    //si queremos disponibles y no disponibles
                    if (disponible.equals("No")) {
                        if (c.getString(0).contains(nombre)) {
                            //añadimos los campos que devuelve la consulta a un objeto libro
                            Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                            //añadimos ese objeto a un ArrayList
                            listaLibros.add(libro);
                            libros++;
                        }
                    }
                    //si queremos solo los disponibles
                    else {
                        if (c.getString(3).equals("Si")) { //cogemos los que solo sean SI disponibles
                            if (c.getString(0).contains(nombre)) { //cogemos los que contengan el nombre del libro
                                //añadimos los campos que devuelve la consulta a un objeto libro
                                Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                                //añadimos ese objeto a un ArrayList
                                listaLibros.add(libro);
                                libros++;
                            }

                        }
                    }

                }

                //SI SE HA PUESTO FILTRO A AUTOR
                else if (!autor.equals("")) {
                    //si queremos disponibles y no disponibles
                    if (disponible.equals("No")) {
                        if (c.getString(1).contains(autor)) {
                            //añadimos los campos que devuelve la consulta a un objeto libro
                            Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                            //añadimos ese objeto a un ArrayList
                            listaLibros.add(libro);
                            libros++;
                        }
                    }
                    //si queremos solo los disponibles
                    else {
                        if (c.getString(3).equals("Si")) { //cogemos los que solo sean SI disponibles
                            if (c.getString(1).contains(autor)) { //cogemos los que contengan el nombre del libro
                                //añadimos los campos que devuelve la consulta a un objeto libro
                                Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                                //añadimos ese objeto a un ArrayList
                                listaLibros.add(libro);
                                libros++;
                            }

                        }
                    }

                }

                //SI SE HA PUESTO FILTRO A GENERO
                else if (!genero.equals("Todos")) {
                    //si queremos disponibles y no disponibles
                    if (disponible.equals("No")) {
                        if (c.getString(2).equals(genero)) {
                            //añadimos los campos que devuelve la consulta a un objeto libro
                            Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                            //añadimos ese objeto a un ArrayList
                            listaLibros.add(libro);
                            libros++;
                        }
                    }
                    //solo disponible
                    else {
                        if (c.getString(3).equals("Si")) { //cogemos los que solo sean SI disponibles
                            if (c.getString(2).contains(genero)) { //cualquier libro que contenga nombre

                                //añadimos los campos que devuelve la consulta a un objeto libro
                                Libro libro = new Libro(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                                //añadimos ese objeto a un ArrayList
                                listaLibros.add(libro);
                                libros++;
                            }

                        }
                    }

                }

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor

            //aladimos el array a un adapter y ese adapter al ReciclerView
            AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
            rvLibros.setAdapter(adapter);

            //si no encuentra ningún libro en la bbdd salta por excepcion
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No existen libros", Toast.LENGTH_LONG).show();
        }
        db.close();
    }*/
