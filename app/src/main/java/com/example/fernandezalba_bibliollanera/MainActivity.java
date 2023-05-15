package com.example.fernandezalba_bibliollanera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //declaraciones
    private EditText etUsuario, etContraseña;

    //instancia para definir las acciones de la bbdd
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertaAdmin();

        //iniciamos las variables
        etUsuario = findViewById(R.id.etUsuario);
        etContraseña = findViewById(R.id.etContraseña);


    }
    //METODOS PARA EL INICIO DE SESION
    //ALTA
    //metodo que da de alta un usuario
    public void darAlta(View vista) {

        //comporbar ususario da true si ya existe
        if (comprobarUsuario()) {
            Toast.makeText(getApplicationContext(), "El ususario ya existe", Toast.LENGTH_LONG).show();


            //si no existe usuario lo damos de alta
        } else {
            String usuario, contraseña;

            usuario = etUsuario.getText().toString();
            contraseña = etContraseña.getText().toString();

            //comprobamos que se estan introduciendo datos
            if (usuario.length() == 9 && contraseña.length() >= 6) {

                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD_Usuario.ID_USUSARIO, usuario);
                values.put(Estructura_BBDD_Usuario.CONTRASEÑA_USUARIO, contraseña);
                values.put(Estructura_BBDD_Usuario.TRABAJADOR_USUARIO, "0"); //da de alta como no trabajadro
                //El resto de valores se rellenan con espacios en blanco y luego ususario podrá modificarlos
                values.put(Estructura_BBDD_Usuario.NOMBRE_USUSARIO, "");
                values.put(Estructura_BBDD_Usuario.LIBRO_USUARIO, "");



                db.insert(Estructura_BBDD_Usuario.TABLA_USUSARIO, null, values);

                Toast.makeText(getApplicationContext(), "Registro guardado correctamente", Toast.LENGTH_LONG).show();

                etUsuario.setText("");
                etContraseña.setText("");

                db.close();

            }else {
                if (usuario.length()!=9) Toast.makeText(getApplicationContext(), "DNI incorrecto", Toast.LENGTH_LONG).show();
                if (contraseña.length()<6) Toast.makeText(getApplicationContext(), "Contraseña demasiado corta", Toast.LENGTH_LONG).show();
            }

        }
    }

    //metodo que comprueba si un ususario ya existe
    private boolean comprobarUsuario(){
        //SI EL USUARIO YA EXISTE DA TRUE
        boolean existe = false;
        String usuario = etUsuario.getText().toString();

        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Usuario.ID_USUSARIO,
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
                //se guarda el usuario
                String usu = c.getString(0);

                if (usuario.equals(usu)){
                    existe = true;
                }
            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        }
        db.close();
        return existe;
    }

    private void insertaAdmin(){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Estructura_BBDD_Usuario.ID_USUSARIO, "71730659F");
        values.put(Estructura_BBDD_Usuario.CONTRASEÑA_USUARIO, "123456");
        values.put(Estructura_BBDD_Usuario.NOMBRE_USUSARIO, "Alba Fernandez");
        values.put(Estructura_BBDD_Usuario.LIBRO_USUARIO, " ");
        values.put(Estructura_BBDD_Usuario.TRABAJADOR_USUARIO, "1");


        db.insert(Estructura_BBDD_Usuario.TABLA_USUSARIO, null, values);

    }

    //ENTRAR

    //metodo que entra si ususario y contraseña son correctos
    public void entrar(View vista){
        if (compruebaAdmin() && comprobarContraseña()){
            Intent i = new Intent(this, Principal.class);
            Bundle b = new Bundle();
            Bundle a = new Bundle();
            b.putString("idUsuario", etUsuario.getText().toString());
            a.putString("admin", "Si");
            i.putExtras(b);
            i.putExtras(a);
            startActivity(i);

        }else{
            if (comprobarContraseña()){
                Intent i = new Intent(this, Principal.class);
                Bundle b = new Bundle();
                Bundle a = new Bundle();
                b.putString("idUsuario", etUsuario.getText().toString());
                a.putString("admin", "No");
                i.putExtras(b);
                i.putExtras(a);
                startActivity(i);
            }

        }

        etUsuario.setText("");
        etContraseña.setText("");
    }
    //metodo que comprueba si la contraseña esta correcta devuelve true o false
    public boolean comprobarContraseña() {
        boolean valida = false;
        if (!comprobarUsuario()) {
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        } else {
            String usuario = etUsuario.getText().toString();
            String contraseña = etContraseña.getText().toString();
            //me conecto en modo lectura
            SQLiteDatabase db = helper.getReadableDatabase();
            //los campos que devuelve la consulta
            String[] projection = {
                    Estructura_BBDD_Usuario.ID_USUSARIO,
                    Estructura_BBDD_Usuario.CONTRASEÑA_USUARIO
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
                    //se guarda el usuario y la contraseña de la bbdd
                    String usu = c.getString(0);
                    String con = c.getString(1);

                    if (usuario.equals(usu)) {
                        if(contraseña.equals(con)){
                            valida=true;
                        }else Toast.makeText(getApplicationContext(), "Contraseña eronea", Toast.LENGTH_LONG).show();
                    }

                } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
                c.close();//cerrar el cursor

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No hay usuarios", Toast.LENGTH_LONG).show();
            }
            db.close();
        }
        return valida;
    }

    public boolean compruebaAdmin(){
        boolean admin=false;
        String usuario = etUsuario.getText().toString();

        //me conecto en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        //los campos que devuelve la consulta
        String[] projection = {
                Estructura_BBDD_Usuario.ID_USUSARIO,
                Estructura_BBDD_Usuario.TRABAJADOR_USUARIO,
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
                //se guarda el usuario
                String usu = c.getString(0);

                if (usuario.equals(usu) && c.getString(1).equals("1")){
                    admin=true;

                }
            } while (c.moveToNext()); //me devuelve el siguiente resultado y si no hay devuelve folse y sale del grupo
            c.close();//cerrar el cursor

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        }
        db.close();
        return admin;
    }
}