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

import java.util.Locale;

public class AdministrarUsuarios extends AppCompatActivity {

    private EditText etDNI;

    private TextView tvLibroUsu;

    private String idUsuario = "", admin = "";

    //instancia para definir las acciones de la bbdd que se debe poner en todas las activitys
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_usuarios);

        //sacamos el dato de idUsuario
        Bundle b = getIntent().getExtras();
        idUsuario = b.getString("idUsuario");

        Bundle a = getIntent().getExtras();
        admin = a.getString("admin");

        etDNI = findViewById(R.id.etDNI);

        tvLibroUsu = findViewById(R.id.tvLibroUsu);


    }

    public void borrarUsuario(View vista) {

        String DNI = etDNI.getText().toString();

        SQLiteDatabase db = helper.getWritableDatabase();

        //me conecto en modo lectura
        SQLiteDatabase dbR = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Usuario.ID_USUSARIO,
                Estructura_BBDD_Usuario.LIBRO_USUARIO
        };

        try {
            //esto es la query
            Cursor c = dbR.query(
                    Estructura_BBDD_Usuario.TABLA_USUSARIO,
                    projection,
                    Estructura_BBDD_Usuario.ID_USUSARIO.toLowerCase(Locale.ROOT) + " LIKE '" + DNI.toLowerCase() + "'",//esto es el where para culumnas
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                if (c.getString(1).equals("") && !c.getString(0).equals(idUsuario)) { //si no tiene libro y no intenta borrarse a si mismo

                    //sentencias que borran el usuario
                    String selection = Estructura_BBDD_Usuario.ID_USUSARIO + " LIKE ?";
                    String[] selectionArgs = {DNI};

                    new AlertDialog.Builder(this)
                            .setTitle("Salir")
                            .setMessage("Desea realmente borrar el usuario " + DNI + "?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db.delete(Estructura_BBDD_Usuario.TABLA_USUSARIO, selection, selectionArgs);

                                    Toast.makeText(getApplicationContext(), "Usuario borrado correctamente", Toast.LENGTH_LONG).show();
                                    ;
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else if (c.getString(1).equals("") && c.getString(0).equals(idUsuario)) { //si no tiene libro y no intenta borrarse a si mismo

                    //sentencias que borran el usuario
                    String selection = Estructura_BBDD_Usuario.ID_USUSARIO + " LIKE ?";
                    String[] selectionArgs = {DNI};

                    new AlertDialog.Builder(this)
                            .setTitle("Salir")
                            .setMessage("Deseas borrar tu usuario ?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db.delete(Estructura_BBDD_Usuario.TABLA_USUSARIO, selection, selectionArgs);
                                    abrirMain();
                                    Toast.makeText(getApplicationContext(), "Has borrado tu usuario", Toast.LENGTH_LONG).show();
                                    ;
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else
                    Toast.makeText(this, "No se puede borrar un usuario con un libro", Toast.LENGTH_SHORT).show();


            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor


            //si no encuentra ningún ususario en la bbdd salta por excepcion
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No existe ese usuario", Toast.LENGTH_LONG).show();

            dbR.close();
            db.close();


        }

    }

    public void mostrarLibro(View vista) {
        String DNI = etDNI.getText().toString();


        //me conecto en modo lectura
        SQLiteDatabase dbR = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Usuario.ID_USUSARIO,
                Estructura_BBDD_Usuario.LIBRO_USUARIO
        };

        try {
            //esto es la query
            Cursor c = dbR.query(
                    Estructura_BBDD_Usuario.TABLA_USUSARIO,
                    projection,
                    Estructura_BBDD_Usuario.ID_USUSARIO.toLowerCase(Locale.ROOT) + " LIKE '" + DNI.toLowerCase() + "'",//esto es el where para culumnas
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                tvLibroUsu.setText(c.getString(1));
                if (c.getString(1).equals(""))
                {Toast.makeText(getApplicationContext(), "Este usuario no tiene libro", Toast.LENGTH_LONG).show();}

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor


            //si no encuentra ningún ususario en la bbdd salta por excepcion
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Este usuario no existe", Toast.LENGTH_LONG).show();
        }
    }

    public void convertirTrabajador(View vista) {

        String DNI = etDNI.getText().toString();
        //me conecto en modo escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        //me conecto en modo lectura
        SQLiteDatabase dbR = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Usuario.ID_USUSARIO,
                Estructura_BBDD_Usuario.TRABAJADOR_USUARIO
        };

        try {
            //esto es la query
            Cursor c = dbR.query(
                    Estructura_BBDD_Usuario.TABLA_USUSARIO,
                    projection,
                    Estructura_BBDD_Usuario.ID_USUSARIO.toLowerCase(Locale.ROOT) + " LIKE '" + DNI.toLowerCase() + "'",//esto es el where para culumnas
                    null,
                    null,
                    null,
                    null //si se quiere ordenar se pone Estructura_BBDD.NOMBRE_COLUMNA
            );
            c.moveToFirst();
            do {
                if (c.getString(1).equals("1"))
                {Toast.makeText(this, "Este usuario ya es trabajador", Toast.LENGTH_SHORT).show();}

                else {
                    //Primero se actualiza la tabla libro
                    //se guarda en values el dato de que no esta el libro disponible
                    ContentValues values = new ContentValues();
                    values.put(Estructura_BBDD_Usuario.TRABAJADOR_USUARIO, "1");

                    String selection = Estructura_BBDD_Usuario.ID_USUSARIO + " LIKE ?";
                    String[] selectionArgs = {DNI};
                    //instruccion que actualiza la tabla libro
                    db.update(Estructura_BBDD_Usuario.TABLA_USUSARIO, values, selection, selectionArgs);
                    Toast.makeText(getApplicationContext(), "usuario convertido a trabajador", Toast.LENGTH_LONG).show();
                }

            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor


            //si no encuentra ningún ususario en la bbdd salta por excepcion
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Este usuario no existe", Toast.LENGTH_LONG).show();
        }

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