package velo.uned.velocimetro.servicios;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import velo.uned.velocimetro.dao.RutaDAO;
import velo.uned.velocimetro.modelo.Medicion;
import velo.uned.velocimetro.modelo.Ruta;

/**
 * Created by Alvaro on 19/03/2018.
 */

public class RutaServicios {
    private RutaDAO rutaDAO;
    public ArrayList<Ruta> rutalist;
    //  CONSTRUCTOR DE RUTASERVICIOS
    public RutaServicios(Context context) {
        rutaDAO = new RutaDAO(context);
    }
    // FUNCION PARA GUARDAR LA RUTA EN LA BASE DE DATOS
    public boolean addRuta(Ruta ruta) {
        try {
            return  rutaDAO.insertar(ruta);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Guardar Ruta" + e.getMessage());
        }

    }
    // FUNCION PARA GUARDAR UNA LISTA DE RUTAS EN LA BASE DE DATOS
    public boolean addAllRuta(ArrayList<Ruta> rutalist,Medicion id) {
        try {
            return  rutaDAO.insertar(rutalist,id);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Guardar Ruta" + e.getMessage());
        }

    }
    // FUNCION PARA MODIFICIAR LA RUTA EN LA BASE DE DATOS
    public boolean updateRuta(Ruta ruta) {
        try {
            return  rutaDAO.alterar(ruta);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Actualizar la Ruta" + e.getMessage());
        }

    }
    // FUNCION PARA BORRAR LA RUTA DE LA BASE DE DATOS
    public boolean deleteRuta(Ruta ruta) {
        try {
            return  rutaDAO.borrar(ruta);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Borrar Ruta" + e.getMessage());
        }

    }
    // FUNCION PARA OBTENER LA RUTA DE LA BASE DE DATOS
    public Ruta getRuta(Ruta ruta) {
        try {
            Ruta nuRuta = rutaDAO.getRuta(ruta.getId());
            return nuRuta;
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Buscar Ruta" + e.getMessage());
        }
    }
    // FUNCION PARA OBTENER TODAS LAS RUTAS DE LA BASE DE DATOS
    public void getallRutaId(long id) {
        try {
            rutalist = rutaDAO.listar(id);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Buscar las Rutas" + e.getMessage());
        }

    }
}
