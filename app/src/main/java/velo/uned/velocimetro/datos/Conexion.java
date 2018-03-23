package velo.uned.velocimetro.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import velo.uned.velocimetro.modelo.Medicion;
import velo.uned.velocimetro.modelo.Ruta;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.util.TripleDES;


/**
 * Created by alexa on 13/03/2018.
 */

public class Conexion extends SQLiteOpenHelper {
    private static final String Nombre_Base = "base.bd";
    private static final int version =1;


    public Conexion(Context context) {
        super(context, Nombre_Base, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        TripleDES des = new TripleDES();
        try {

            String crear_tabla_persona = "CREATE TABLE " + Medicion.tabla  + "("
                    + Medicion.campo_id  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + Medicion.campo_distancia + " DOUBLE, "
                    + Medicion.campo_fecha_Inicio + " DATE, "
                    + Medicion.campo_fecha_Fin + " DOUBLE, "
                    + Medicion.campo_velocidad + " DOUBLE )";
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
                    + User.campo_rol + " TEXT )";
            String crear_usuario_administrador="INSERT INTO" +User.tabla+ "("
                    + User.campo_id +","
                    + User.campo_usuario + ","
                    + User.campo_contraseña + ")"
                    + " VALUES ( "
                    + "1 ,"
                    + "'Administrador', "
                    + des.encrypt("password").toString() +","
                    + "'Alvaro', "
                    + "'Uyaguari' ,"
                    + "'admin'"
                    +")";

            db.execSQL(crear_tabla_persona);
            db.execSQL(crear_tabla_ruta);
            db.execSQL(crear_tabla_users);
            db.execSQL(crear_usuario_administrador);

        }catch (Exception ex){
            Log.d(this.getClass().toString(), ex.getMessage());
        }
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS medicion");
        db.execSQL("DROP TABLE IF EXISTS ruta");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
