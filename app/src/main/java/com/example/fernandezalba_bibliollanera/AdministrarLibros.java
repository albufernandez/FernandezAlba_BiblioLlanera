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
import java.util.Locale;

public class AdministrarLibros extends AppCompatActivity {
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
        setContentView(R.layout.activity_administrar_libros);

        //sacamos el dato de idUsuario
        Bundle b = getIntent().getExtras();
        idUsuario = b.getString("idUsuario");

        Bundle a = getIntent().getExtras();
        admin = a.getString("admin");

        rvLibros = findViewById(R.id.rvLibros);
        etNombre = findViewById(R.id.etNombre);
        etAutor = findViewById(R.id.etAutor);
        spGenero = findViewById(R.id.spGenero);
        rbLugo = findViewById(R.id.rbLugo);
        rbLugo.setChecked(true);
        rbPosada = findViewById(R.id.rbPosada);

        rvLibros = findViewById(R.id.rvLibros);
        cbDisponible = findViewById(R.id.cbDisponible);

        // Establecer layout del RecyclerView
        rvLibros.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listaLibros = new ArrayList<Libro>();

        // Crear adaptador pasándole como parámetro el ArrayList
        AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);

        // Asignar al RecyclerView el adaptador
        rvLibros.setAdapter(adapter);
    }

    //METODO INSERTAR
    public void insertarLibro(View vista) {
        String nombre = etNombre.getText().toString();
        String autor = etAutor.getText().toString();
        String genero = compruebaGenero(spGenero);
        String disponible = "Si";
        String biblio = "";
        if (rbLugo.isChecked()) biblio = "01";
        if (rbPosada.isChecked()) biblio = "02";

        if (nombre.equals("") || autor.equals("") || genero.equals("Todos") || disponible.equals("No") || biblio.equals("")) {
            Toast.makeText(this, "Algun campo por rellenar", Toast.LENGTH_SHORT).show();
        } else {
            //insertamos los libros
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values1 = new ContentValues();
            values1.put(Estructura_BBDD_Libro.NOMBRE_LIBRO, nombre);
            values1.put(Estructura_BBDD_Libro.AUTOR_LIBRO, autor);
            values1.put(Estructura_BBDD_Libro.GENERO_LIBRO, genero);
            values1.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, disponible);
            values1.put(Estructura_BBDD_Libro.BIBLIOTECA_LIBRO, biblio);

            db.insert(Estructura_BBDD_Libro.TABLA_LIBRO, null, values1);

            Toast.makeText(getApplicationContext(), "Se ha insertado el libro en la aplicación", Toast.LENGTH_LONG).show();
            db.close();
        }

        // Crear adaptador pasándole como parámetro el ArrayList
        AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
        rvLibros.setAdapter(adapter);

        buscar(vista);
    }

    //metodo que busca un libro en la base de datos
    public void buscar(View vista) {

        String nombre = etNombre.getText().toString();
        String autor = etAutor.getText().toString();

        String genero = compruebaGenero(spGenero);
        String generoCon = "";
        if (genero.equals("Todos")) {
            generoCon = "%%"; //coge cualquier string que contenga una letra de la a a la z y con lo que sea por delante y por detras
        } else generoCon = genero;


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
                    Estructura_BBDD_Libro.NOMBRE_LIBRO.toLowerCase(Locale.ROOT) + " LIKE '%" + nombre.toLowerCase() + "%' AND " + Estructura_BBDD_Libro.AUTOR_LIBRO.toLowerCase() + " LIKE '%" + autor.toLowerCase() + "%' AND "
                            + Estructura_BBDD_Libro.GENERO_LIBRO + " LIKE '" + generoCon + "' AND " + Estructura_BBDD_Libro.DISPONIBLE_LIBRO + " LIKE '" + disponible +
                            "' AND " + Estructura_BBDD_Libro.BIBLIOTECA_LIBRO + " LIKE '" + biblio + "'",//esto es el where para culumnas
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
        //aladimos el array a un adapter y ese adapter al ReciclerView
        AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
        rvLibros.setAdapter(adapter);

        db.close();


    }

    public void borrar(View vista) {
        //si la busqueda devuelve mas de un libro sale una alerta
        if (listaLibros.size() > 1) {
            Toast.makeText(this, "Solo se puede borrar un libro", Toast.LENGTH_SHORT).show();
        } else if (listaLibros.size() == 1) {
            Libro libro = listaLibros.get(0);
            //se comprueba que el libro si pueda borrar

            //Si el libro esta disponible
            if (libro.getDisponibleLibro().equals("Si")) {

                String nombreBorrar = libro.getNombreLibro();

                SQLiteDatabase db = helper.getWritableDatabase();

                //sentencias que borran el producto
                String selection = Estructura_BBDD_Libro.NOMBRE_LIBRO + " LIKE ?";
                String[] selectionArgs = {String.valueOf(nombreBorrar)};
                db.delete(Estructura_BBDD_Libro.TABLA_LIBRO, selection, selectionArgs);

                Toast.makeText(getApplicationContext(), "Registro borrado correctamente", Toast.LENGTH_LONG).show();

                listaLibros.clear();

                // Crear adaptador pasándole como parámetro el ArrayList
                AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
                rvLibros.setAdapter(adapter);
            } else
                Toast.makeText(this, "No se pueden borra libros no disponibles", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, "Debe haber un libro para poder borrarlo", Toast.LENGTH_SHORT).show();

        // Crear adaptador pasándole como parámetro el ArrayList
        AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
        rvLibros.setAdapter(adapter);

    }

    public void devolver(View vista) {
        //si la busqueda devuelve mas de un libro sale una alerta
        if (listaLibros.size() > 1) {
            Toast.makeText(this, "Solo se puede devolver un libro", Toast.LENGTH_SHORT).show();
        } else if (listaLibros.size() == 1) {
            Libro libro = listaLibros.get(0);
            //se comprueba que el libro si pueda borrar

            //Si el libro no esta disponible
            if (libro.getDisponibleLibro().equals("No")) {
                String NomLibro = libro.getNombreLibro();
                //me conecto en modo escritura
                SQLiteDatabase db = helper.getWritableDatabase();

                //Primero se actualiza la tabla libro
                //se guarda en values el dato de que no esta el libro disponible
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD_Libro.DISPONIBLE_LIBRO, "Si");

                String selection = Estructura_BBDD_Libro.NOMBRE_LIBRO + " LIKE ?";
                String[] selectionArgs = {NomLibro};
                //instruccion que actualiza la tabla libro
                db.update(Estructura_BBDD_Libro.TABLA_LIBRO, values, selection, selectionArgs);

                //buscamos que ususario tiene el libro que se quiere devolver para actualizar tambien su tabla
                String usu = buscarUsu(libro.getNombreLibro());

                //Ahora se actualiza la tabla usuario quitando el libro reservado
                ContentValues valuesU = new ContentValues();
                valuesU.put(Estructura_BBDD_Usuario.LIBRO_USUARIO, "");

                String selectionU = Estructura_BBDD_Usuario.ID_USUSARIO + " LIKE ?";
                String[] selectionArgsU = {usu};

                //instruccion que actualiza la tabla usuario
                db.update(Estructura_BBDD_Usuario.TABLA_USUSARIO, valuesU, selectionU, selectionArgsU);

                Toast.makeText(this, "Libro devuelto correctamente", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(this, "No se pueden devolver libros ya disponibles", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, "Debe haber un libro para poder devolverlo", Toast.LENGTH_SHORT).show();


        buscar(vista);

        // Crear adaptador pasándole como parámetro el ArrayList
        AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
        rvLibros.setAdapter(adapter);


         rbLugo.setChecked(true);

    }

    //metodo que busca que usuario tiene un libro
    private String buscarUsu(String nombreLibro) {
        String dniUsu = "";

        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Usuario.ID_USUSARIO,
                Estructura_BBDD_Usuario.LIBRO_USUARIO
        };

        try {
            //esto es la query
            Cursor c = db.query(
                    Estructura_BBDD_Usuario.TABLA_USUSARIO,
                    projection,
                    Estructura_BBDD_Usuario.LIBRO_USUARIO.toLowerCase(Locale.ROOT) + " LIKE '%" + nombreLibro.toLowerCase() + "%'",//esto es el where para culumnas
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                dniUsu = c.getString(0);

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor


            //si no encuentra ningún libro en la bbdd salta por excepcion
        } catch (Exception e) {
        }

        return dniUsu;
    }
    //metodo que pone un libro (o varios) como no disponible pero no esta asigando a ningun usuario
    public void bloquear(View vista) {
        //si la busqueda devuelve mas de un libro sale una alerta
        if (listaLibros.size() >= 1) {
            int libros = listaLibros.size();

            for (int i = 0; i < libros; i++) {
                Libro libro = listaLibros.get(i);

                //Si el libro si esta disponible (solo se pueden bloquear libros disponibles)
                if (libro.getDisponibleLibro().equals("Si")) {

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


                    Toast.makeText(this, "Libro(s) bloqueado(s) correctamente", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(this, "No se pueden bloquear libros no disponibles", Toast.LENGTH_SHORT).show();

            }

        } else
            Toast.makeText(this, "Debe haber un libro o mas para poder bloquearlos", Toast.LENGTH_SHORT).show();


        buscar(vista);

        // Crear adaptador pasándole como parámetro el ArrayList
        AdapterDatos_Libro adapter = new AdapterDatos_Libro(listaLibros);
        rvLibros.setAdapter(adapter);


    }


    public String compruebaGenero(Spinner spGenero) {
        String genero = "";
        long sel = spGenero.getSelectedItemId();
        if (sel == 0) genero = "Todos";
        if (sel == 1) genero = "Fantasia";
        if (sel == 2) genero = "Policiaco";
        if (sel == 3) genero = "Romantico";
        if (sel == 4) genero = "Terror";
        return genero;
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
    public void abrirLibros(View vista) {
        Intent i = new Intent(this, Libros.class);
        Bundle b = new Bundle();
        Bundle a = new Bundle();
        b.putString("idUsuario", idUsuario);
        a.putString("admin", admin);
        i.putExtras(b);
        i.putExtras(a);
        startActivity(i);
    }

    public void abrirBibliotecas(View vista) {
        Intent i = new Intent(this, Bibliotecas.class);
        Bundle b = new Bundle();
        Bundle a = new Bundle();
        b.putString("idUsuario", idUsuario);
        a.putString("admin", admin);
        i.putExtras(b);
        i.putExtras(a);
        startActivity(i);
    }

    public void abrirMiPerfil(View vista) {
        Intent i = new Intent(this, MiPerfilAdmin.class);
        Bundle b = new Bundle();
        Bundle a = new Bundle();
        b.putString("idUsuario", idUsuario);
        a.putString("admin", admin);
        i.putExtras(b);
        i.putExtras(a);
        startActivity(i);
    }

    //METODO SALIR
    public void salir(View vista) {
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

    public void abrirPrincipal(View vista) {
        Intent i = new Intent(this, Principal.class);
        Bundle b = new Bundle();
        Bundle a = new Bundle();
        b.putString("idUsuario", idUsuario);
        a.putString("admin", admin);
        i.putExtras(b);
        i.putExtras(a);
        startActivity(i);
    }

    public void abrirMain() {
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