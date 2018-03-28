package velo.uned.velocimetro.servicios;

import android.content.Context;

import java.util.ArrayList;

import velo.uned.velocimetro.dao.RutaDAO;
import velo.uned.velocimetro.modelo.Medicion;
import velo.uned.velocimetro.modelo.Ruta;

/**
 * Created by alexa on 19/03/2018.
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

        return  rutaDAO.insertar(ruta);
    }
    // FUNCION PARA GUARDAR UNA LISTA DE RUTAS EN LA BASE DE DATOS
    public boolean addAllRuta(ArrayList<Ruta> rutalist,Medicion id) {

        return  rutaDAO.insertar(rutalist,id);
    }
    // FUNCION PARA MODIFICIAR LA RUTA EN LA BASE DE DATOS
    public boolean updateRuta(Ruta ruta) {

        return  rutaDAO.alterar(ruta);
    }
    // FUNCION PARA BORRAR LA RUTA DE LA BASE DE DATOS
    public boolean deleteRuta(Ruta ruta) {

        return  rutaDAO.borrar(ruta);
    }
    // FUNCION PARA OBTENER LA RUTA DE LA BASE DE DATOS
    public Ruta getRuta(Ruta ruta) {
        Ruta nuRuta = rutaDAO.getRuta(ruta.getId());
        return nuRuta;
    }
    // FUNCION PARA OBTENER TODAS LAS RUTAS DE LA BASE DE DATOS
    public void getallRutaId(long id) {
        rutalist = rutaDAO.listar(id);
    }
}
