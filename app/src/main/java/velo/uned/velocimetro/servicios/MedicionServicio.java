package velo.uned.velocimetro.servicios;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import velo.uned.velocimetro.dao.MedicionDAO;
import velo.uned.velocimetro.modelo.Medicion;

/**
 * Created by Alvaro on 19/03/2018.
 */

public class MedicionServicio {
    private MedicionDAO medicionDAO;
    public ArrayList<Medicion> listMedicion;
    // CONSTRUCTOR DE MEDICIONSERVICIO
    public MedicionServicio(Context context) {
        medicionDAO = new MedicionDAO(context);
    }
    // FUNCION PARA GUARDAR LA MEDICION EN LA BASE DE DATOS
    public boolean addMedicion(Medicion medicion) {
        try {
            return  medicionDAO.insertar(medicion);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Guardar Medicion" + e.getMessage());
        }


    }
    // FUNCION PARA ACTUALIZAR LA MEDICION EN LA BASE DE DATOS
    public boolean updateMedicion(Medicion medicion) {
        try {
            return medicionDAO.alterar(medicion);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Modificar Medicion" + e.getMessage());
        }


    }
    public boolean deleteMedicion(Medicion medicion) {
        try {
            return medicionDAO.borrar(medicion);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Eliminar Medicion" + e.getMessage());
        }


    }
    // FUNCION PARA OBTENER LA MEDICION EN LA BASE DE DATOS
    public Medicion getMedicion(Medicion medicion) {
        try {
            Medicion nuMedicion = medicionDAO.getMedicion(medicion.getId());
            return nuMedicion;
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Obtener Medicion" + e.getMessage());
        }

    }
    // FUNCION PARA OBTENER TODAS LAS MEDICIONES DE LA BASE DE DATOS
    public void getallMedicion(long id) throws SQLiteException {
        try {

            listMedicion = medicionDAO.listar(id);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Obtener las Mediciones" + e.getMessage());
        }
    }
    //Lista todas las mediciones y retorna un objeto de tipo Cursor
    public Cursor listarCursor() {
        return medicionDAO.listarCursor();
    }
}
