package velo.uned.velocimetro.servicios;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import velo.uned.velocimetro.dao.UsersDAO;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.util.TripleDES;


/**
 * Created by alexa on 20/03/2018.
 */

public class UsersServicio   {
    private UsersDAO usersDAO;
    private TripleDES des;
    private User nuUser;
    public ArrayList<User> listUser;
    // CONSTRUCTOR DE USERSERVICIOS
    public UsersServicio(Context context) {
        usersDAO = new UsersDAO(context);
        des=new TripleDES();
        nuUser=new User();
    }
    // FUNCION PARA GUARDAR EL USER EN LA BASE DE DATOS
    public boolean addUser(User user,String pass) throws SQLiteException{
        try {
            return usersDAO.insertar(user,pass);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Guardar el Usuario" + e.getMessage());
        }

    }
    // FUNCION PARA MODIFICAR EL CAMPO INTENTO DE USER DE LA BASE DE DATOS
    public boolean update(User users,String pass) throws SQLiteException{
        try {
            return usersDAO.alterar(users,pass);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Actualizar el Usuario" + e.getMessage());
        }

    }
    // FUNCION PARA MODIFICAR EL CAMPO INTENTO DE USER DE LA BASE DE DATOS
    public boolean updateIntento(User users) throws SQLiteException{
        boolean dec=false;
        try{
            if (!users.getUser().equals("admin")){
                nuUser=usersDAO.getUser(users);
                if (!nuUser.equals(null)){
                    nuUser.setIntento(nuUser.getIntento()+1);
                    dec= usersDAO.alterarIntento(nuUser);
                }
            }
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error en la Actualizacion del Usuario" + e.getMessage());
        }
        return dec;
    }
    // FUNCION PARA MODIFICAR EL CAMPO INTENTO CUANDO SE LOGUEA CORRECTAMENTE EN LA BASE DE DATOS DE USER
    public boolean updateNuevo(User users) throws SQLiteException{
        boolean dec=false;
        try{
            if (!users.getUser().equals("admin")){
                users.setIntento(0);
                dec= usersDAO.alterarIntento(users);
            }
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error en la Actualizacion del Usuario" + e.getMessage());
        }

        return dec;
    }
    // FUNCION PARA BORRAR EL USER DE LA BASE DE DATOS
    public boolean deleteUser(User users) throws SQLiteException{

        return usersDAO.borrar(users);
    }
    // FUNCION PARA OBTENER EL USER DE LA BASE DE DATOS
    public User getUser(User users) {
        try {
            return usersDAO.getUserID(users);
        }catch (SQLiteException e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error al Borrar el Usuario" + e.getMessage());
        }
    }
    // FUNCION PARA VALIDAR EL LOGEO DE UN USUARIO Y VER SI SU CUENTA ESTA ACTIVA
    public boolean validarLogin(User user, final String password) throws SQLiteException {
        boolean dec=false;
        nuUser = usersDAO.getUser(user);
        try{
            if (des.decrypt(nuUser.getPass()).equals(des.decrypt(password))) {
                if (nuUser.getEstado().equals("Activa")){
                    dec=true;
                }
            }
            user.setId(nuUser.getId());
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error en la ecriptación" + e.getMessage());
        }
        return dec;
    }
    // FUNCION PARA LISTAR TODOS LOS USER DE LA BASE DE DATOS
    public void listar(){
        listUser= usersDAO.listar();
    }
}
