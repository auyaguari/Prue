package velo.uned.velocimetro.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import velo.uned.velocimetro.modelo.Medicion;
import velo.uned.velocimetro.modelo.Ruta;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.util.TripleDES;


/**
 * Created by Alvaro on 13/03/2018.
 */

public class Conexion extends SQLiteOpenHelper {
    private static final String Nombre_Base = "base.bd";
    private static final int version =1;


    public Conexion(Context context) {

        super(context, Nombre_Base, null, version);

    }
    // CREA LA BASE DE DATOS Y EL USUARIO ADMIN
    @Override
    public void onCreate(SQLiteDatabase db) throws SQLiteException {

        TripleDES des = new TripleDES();
        try {

            String crear_tabla_persona = "CREATE TABLE " + Medicion.tabla  + "("
                    + Medicion.campo_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + Medicion.campo_distancia + " DOUBLE, "
                    + Medicion.campo_fecha_Inicio + " DATE, "
                    + Medicion.campo_fecha_Fin + " DOUBLE, "
                    + Medicion.campo_velocidad + " DOUBLE, "
                    + Medicion.campo_id_user + " INTEGER )";
            String crear_tabla_ruta = "CREATE TABLE " + Ruta.tabla  + "("
                    + Ruta.campo_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + Ruta.campo_latitud + " DOUBLE, "
                    + Ruta.campo_longitud + " DOUBLE, "
                    + Ruta.campo_id_medicion + " INTEGER )";
            String crear_tabla_users = "CREATE TABLE " + User.tabla  + "("
                    + User.campo_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + User.campo_usuario + " TEXT, "
                    + User.campo_contraseña + " TEXT, "
                    + User.campo_nombre + " TEXT, "
                    + User.campo_apellido + " TEXT, "
                    + User.campo_rol + " TEXT, "
                    + User.campo_intentos + " INTEGER, "
                    + User.campo_estado + " TEXT )";
            db.execSQL(crear_tabla_persona);
            db.execSQL(crear_tabla_ruta);
            db.execSQL(crear_tabla_users);

            String pass=des.encrypt("admins1234");
            ContentValues values = new ContentValues();
            values.put(User.campo_nombre,"Alvaro");
            values.put(User.campo_apellido,"Ayaguary");
            values.put(User.campo_rol,"Admin");
            values.put(User.campo_usuario, "admin");
            values.put(User.campo_contraseña, pass);
            values.put(User.campo_intentos, 0);
            values.put(User.campo_estado, "Activa");
            db.insert(User.tabla, null, values);

        }catch (Exception ex) {
            Log.d(this.getClass().toString(), ex.getMessage());
        }
        }
    // VERIFICA QUE NO EXISTA LAS BASES DE DATOS Y SI ES ASI LLAMA A LA FUNCION ONCREATE
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS medicion");
        db.execSQL("DROP TABLE IF EXISTS ruta");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
