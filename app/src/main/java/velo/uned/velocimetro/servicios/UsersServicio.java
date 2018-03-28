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
    public UsersServicio(Context context) {
        usersDAO = new UsersDAO(context);
        des=new TripleDES();
        nuUser=new User();
        listar();
    }

    public boolean addUser(User user,String pass) throws SQLiteException{

        return usersDAO.insertar(user,pass);
    }

    public boolean updateIntento(User users) throws SQLiteException{
        boolean dec=false;
        if (!users.getUser().equals("admin")){
            nuUser=usersDAO.getUser(users);
            if (!nuUser.equals(null)){
                nuUser.setIntento(nuUser.getIntento()+1);
                dec= usersDAO.alterarIntento(nuUser);
            }
        }
        return dec;
    }
    public boolean updateNuevo(User users) throws SQLiteException{
        boolean dec=false;
        if (!users.getUser().equals("admin")){
            nuUser=usersDAO.getUser(users);
            if (!nuUser.equals(null)){
                nuUser.setIntento(0);
                dec= usersDAO.alterarIntento(nuUser);
            }
        }
        return dec;
    }

    public boolean deleteUser(User users) throws SQLiteException{

        return usersDAO.borrar(users);
    }

    public User getUser(User users) {
      return usersDAO.getUserID(users);
    }
    public boolean validarLogin(User user, final String password) throws SQLiteException {
        boolean dec=false;
        nuUser = usersDAO.getUser(user);
        try{
            if (des.decrypt(nuUser.getPass()).equals(des.decrypt(password))) {
                if (nuUser.getEstado().equals("Activa")){
                    dec=true;
                }
            }
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error en la ecriptación" + e.getMessage());
        }
        return dec;
    }
   public void listar(){
      listUser= usersDAO.listar();
   }
}
