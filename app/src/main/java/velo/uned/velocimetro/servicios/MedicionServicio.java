package velo.uned.velocimetro.servicios;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import velo.uned.velocimetro.dao.MedicionDAO;
import velo.uned.velocimetro.modelo.Medicion;

/**
 * Created by alexa on 19/03/2018.
 */

public class MedicionServicio {
    private MedicionDAO medicionDAO;
    // CONSTRUCTOR DE MEDICIONSERVICIO
    public MedicionServicio(Context context) {
        medicionDAO = new MedicionDAO(context);
    }
    // FUNCION PARA GUARDAR LA MEDICION EN LA BASE DE DATOS
    public boolean addMedicion(Medicion medicion) {

       return  medicionDAO.insertar(medicion);

    }
    // FUNCION PARA ACTUALIZAR LA MEDICION EN LA BASE DE DATOS
    public boolean updateMedicion(Medicion medicion) {

        return medicionDAO.alterar(medicion);

    }
    public boolean deleteMedicion(Medicion medicion) {

        return medicionDAO.borrar(medicion);

    }
    // FUNCION PARA OBTENER LA MEDICION EN LA BASE DE DATOS
    public Medicion getMedicion(Medicion medicion) {
        Medicion nuMedicion = medicionDAO.getMedicion(medicion.getId());
        return nuMedicion;
    }
    // FUNCION PARA OBTENER TODAS LAS MEDICIONES DE LA BASE DE DATOS
    public ArrayList<Medicion> getallMedicion() {
        ArrayList<Medicion> medicionList = medicionDAO.listar();
        return medicionList;
    }
    //Lista todas las mediciones y retorna un objeto de tipo Cursor
    public Cursor listarCursor() {
        return medicionDAO.listarCursor();

    }
}
