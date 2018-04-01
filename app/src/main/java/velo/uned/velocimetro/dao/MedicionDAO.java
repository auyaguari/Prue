package velo.uned.velocimetro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import velo.uned.velocimetro.datos.Conexion;
import velo.uned.velocimetro.modelo.Medicion;

/**
 * Created by Alvaro on 15/03/2018.
 */

public class MedicionDAO {
    Context context;
    Conexion dbsqLite;
    public static ArrayList<Medicion> listaMedicions;
    //CONSTRUCTOR DE MEDICIONDAO
    public MedicionDAO(Context context) {
        this.context = context;
        dbsqLite = new Conexion(context);
    }
    //FUNCION PARA GUARDAR LA MEDICION EN LA BASE DE DATOS
    public boolean insertar(Medicion medicion) {
        SQLiteDatabase db = dbsqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        Long id=null;
        try {
            values.put(medicion.campo_distancia, medicion.getDistance());
            values.put(medicion.campo_fecha_Inicio, String.valueOf(medicion.getFechaInicio()));
            values.put(medicion.campo_fecha_Fin, medicion.getFechafin());
            values.put(medicion.campo_velocidad, medicion.getVelocidadMaxima());
            values.put(medicion.campo_id_user, medicion.getId_User());
            id = db.insert(medicion.tabla, null, values);
            db.close();
            if (id > 0) {
                medicion.setId(id);
            }
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return id > 0;
    }
    // FUNCION PARA MODIFICAR LA MEDICION EN LA BASE DE DATOS ( NO SE UTILIZA AUN)
    public boolean alterar(Medicion medicion) {
        int id=0;
        SQLiteDatabase db = dbsqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(medicion.campo_distancia, medicion.getDistance());
            values.put(medicion.campo_fecha_Inicio, String.valueOf(medicion.getFechaInicio()));
            values.put(medicion.campo_fecha_Fin, medicion.getFechafin());
            String where = medicion.campo_id + " = ?";

            id = db.update(Medicion.tabla, values, where, new String[]{String.valueOf(medicion.getId())});
            db.close();
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return id > 0;
    }

    // FUNCION PARA ELIMINAR LA MEDICION DE LA BASE DE DATOS ( NO SE UTILIZA AUN)
    public boolean borrar(Medicion medicion) {
        int ret=0;
        SQLiteDatabase db = dbsqLite.getWritableDatabase();
        try {
            String where = medicion.campo_id + " = ?";
            ret = db.delete(medicion.tabla, where, new String[]{String.valueOf(medicion.getId())});
            db.close();
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return ret > 0;
    }

    //FUNCION PARA SACAR LOS DATOS DE LA MEDICION DE LA BASE DE DATOS (LA FUNCION DEVUELVE UN OBJETO DE TIPO MEDICION)
    public Medicion getMedicion(Long id){
        SQLiteDatabase db =dbsqLite.getReadableDatabase();

        Medicion medicion =new Medicion();
        try {
            String [] campos={Medicion.campo_distancia,Medicion.campo_fecha_Inicio,Medicion.campo_fecha_Fin,
                    Medicion.campo_velocidad};
            String [] parametro={id.toString()};
            String where = medicion.campo_id + " = ?";
            Cursor cursor = db.query(Medicion.tabla, campos, where, parametro, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            medicion.setId(id);
            medicion.setDistancia(cursor.getDouble(0));
            medicion.setFechaInicio(Date.valueOf(cursor.getString(1)));
            medicion.setFechafin(cursor.getDouble(2));
            medicion.setVelocidad(cursor.getDouble(3));
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }

        return medicion;
    }
    // FUNCION PARA SACAR TODAS LAS MEDICIONES DE LA BASE DE DATOS ( LA FUNCION DEVUELVE UN ARRAY DE TIPO MEDICION)
    public ArrayList<Medicion> listar(long id)  throws SQLiteException{
        SQLiteDatabase db = dbsqLite.getReadableDatabase();
        listaMedicions = new ArrayList<>();
        try {
            String selectQuery = "SELECT  " +
                    Medicion.campo_id + "," +
                    Medicion.campo_distancia + "," +
                    Medicion.campo_fecha_Inicio + "," +
                    Medicion.campo_fecha_Fin + "," +
                    Medicion.campo_velocidad + "," +
                    Medicion.campo_id_user +
                    " FROM " + Medicion.tabla;

            Cursor cursor = db.rawQuery(selectQuery, null);
            Medicion nuMedicion;

            if (cursor.moveToFirst()) {
                do {
                    nuMedicion = new Medicion();
                    nuMedicion.setId(cursor.getLong(0));
                    nuMedicion.setDistancia(cursor.getDouble(1));
                    nuMedicion.setFechafin(cursor.getDouble(3));
                    nuMedicion.setVelocidadMaxima(cursor.getDouble(4));
                    nuMedicion.setId_User(cursor.getLong(5));
                    if (id==nuMedicion.getId_User())
                        listaMedicions.add(nuMedicion);
                } while (cursor.moveToNext());
                db.close();
            }
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Obtener las Mediciones" + e.getMessage());

        }

        return listaMedicions;
    }

    //OTRA FORMA DE SACAR TODAS LAS MEDICIONES DE LA BASE DE DATOS ( NO SE UTILIZA)
    public Cursor listarCursor() {
        SQLiteDatabase db = dbsqLite.getReadableDatabase();
        listaMedicions = new ArrayList<>();

        String selectQuery = "SELECT  " +
                Medicion.campo_id + "," +
                Medicion.campo_distancia + "," +
                Medicion.campo_fecha_Inicio + "," +
                Medicion.campo_fecha_Fin + "," +
                Medicion.campo_velocidad +
                " FROM " + Medicion.tabla;

        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }
    // FUNCION PARA BUSCAR UN OBJETO MEDICION DE LA LISTA DE MEDICIONES ( NO SE UTILIZA )
    public Medicion localizarMedicion(long id) {
        for (Medicion nuMedicion : listaMedicions)
            if (nuMedicion.getId() == id)
                return nuMedicion;
        return null;
    }
}
