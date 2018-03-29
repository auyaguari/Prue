package velo.uned.velocimetro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

import velo.uned.velocimetro.datos.Conexion;
import velo.uned.velocimetro.modelo.Medicion;
import velo.uned.velocimetro.modelo.Ruta;

/**
 * Created by alexa on 19/03/2018.
 */

public class RutaDAO {
    Context context;
    Conexion dbsqLite;
    public static ArrayList<Ruta> listaRutas;

    // CONSTRUCTOR DE LA CLASE RUTADAO
    public RutaDAO(Context context) {
        this.context = context;
        dbsqLite = new Conexion(context);
    }

    // FUNCION PARA GUARDAR LA RUTA EN LA BASE DE DATOS
    public boolean insertar(Ruta ruta) {
        Long id=null;
        SQLiteDatabase db = dbsqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(ruta.campo_latitud, ruta.getLatitud());
            values.put(ruta.campo_longitud, ruta.getLongitud());
            values.put(ruta.campo_id_medicion,ruta.getMedicion().getId());
            id = db.insert(ruta.tabla, null, values);
            db.close();
            if (id > 0) {
                ruta.setId(id);
            }
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }
        return id > 0;
    }
    // FUNCION PARA GUARDAR LA LISTA DE RUTAS EN LA BASE DE DATOS
    public boolean insertar(ArrayList<Ruta> rutalist,Medicion medicion) {
        SQLiteDatabase db = dbsqLite.getWritableDatabase();
        ContentValues values;
        Long id = null;
        try {
            for (Ruta nuRuta : listaRutas){
                nuRuta.setMedicion(medicion);
                values = new ContentValues();
                values.put(nuRuta.campo_latitud, nuRuta.getLatitud());
                values.put(nuRuta.campo_longitud, nuRuta.getLongitud());
                values.put(nuRuta.campo_id_medicion,nuRuta.getMedicion().getId());
                id= db.insert(nuRuta.tabla, null, values);
                if (id > 0) {
                    nuRuta.setId(id);
                }
            }
            db.close();
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return id > 0;
    }
    // FUNCION PARA MODIFICAR LA RUTA EN LA BASE DE DATOS
    public boolean alterar(Ruta ruta) {
        int id=0;
        SQLiteDatabase db = dbsqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(ruta.campo_latitud, ruta.getLatitud());
            values.put(ruta.campo_longitud, ruta.getLongitud());
            values.put(ruta.campo_id_medicion,ruta.getMedicion().getId());
            String where = ruta.campo_id + " = ?";

            id = db.update(ruta.tabla, values, where, new String[]{String.valueOf(ruta.getId())});
            db.close();
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return id > 0;
    }
    // FUNCION PARA BORRAR LA RUTA EN LA BASE DE DATOS
    public boolean borrar(Ruta ruta ) {
        int ret=0;
        SQLiteDatabase db = dbsqLite.getWritableDatabase();
        try {
            String where = ruta.campo_id + " = ?";
            ret = db.delete(ruta.tabla, where, new String[]{String.valueOf(ruta.getId())});
            db.close();
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return ret > 0;
    }
    // FUNCION PARA OBTENER LA RUTA EN LA BASE DE DATOS ( DEVUELVE UN OBJETO RUTA No se Utiliza)
    public Ruta getRuta(Long id){
        SQLiteDatabase db =dbsqLite.getReadableDatabase();
        Ruta ruta  =new Ruta();
        Medicion medicion= new Medicion();
        try {
            String [] campos={Ruta.campo_longitud,Ruta.campo_longitud,Ruta.campo_id_medicion};
            String [] parametro={id.toString()};
            String where = Ruta.campo_id + " = ?";
            Cursor cursor = db.query(Ruta.tabla, campos, where, parametro, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            ruta.setId(id);
            ruta.setLatitud(cursor.getDouble(0));
            ruta.setLongitud(cursor.getDouble(1));
            medicion.setId(cursor.getLong(2));
            ruta.setMedicion(medicion);
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return ruta;
    }
    // FUNCION PARA OBTENER TODAS LAS RUTAS EN LA BASE DE DATOS( DEVUELVE UN ARRAY DE TIPO RUTA)
    public ArrayList<Ruta> listar(long id) {
        SQLiteDatabase db = dbsqLite.getReadableDatabase();
        listaRutas = new ArrayList<>();
        try {
            String selectQuery = "SELECT  " +
                    Ruta.campo_id + "," +
                    Ruta.campo_latitud + "," +
                    Ruta.campo_longitud + "," +
                    Ruta.campo_id_medicion +
                    " FROM " + Ruta.tabla;
            Cursor cursor = db.rawQuery(selectQuery, null);
            Ruta nuRuta;
            Medicion medicion;
            if (cursor.moveToFirst()) {
                do {
                    nuRuta = new Ruta();
                    medicion=new Medicion();
                    nuRuta.setId(cursor.getLong(0));
                    nuRuta.setLatitud(cursor.getDouble(1));
                    nuRuta.setLongitud(cursor.getDouble(2));
                    medicion.setId(cursor.getLong(3));
                    nuRuta.setMedicion(medicion);
                    if (medicion.getId()==id)
                        listaRutas.add(nuRuta);
                } while (cursor.moveToNext());
                db.close();
            }
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return listaRutas;
    }

    // FUNCION PARA BUSCAR LA RUTA POR ID DE LA LISTA DE RUTAS
    public Ruta localizarRuta(long id) {
        for (Ruta nuRuta : listaRutas)
            if (nuRuta.getId() == id)
                return nuRuta;
        return null;
    }
}
