package velo.uned.velocimetro.servicios;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import velo.uned.velocimetro.dao.UsersDAO;
import velo.uned.velocimetro.modelo.User;


/**
 * Created by alexa on 20/03/2018.
 */

public class UsersServicio   {
    private UsersDAO usersDAO;

    public UsersServicio(Context context) {
        usersDAO = new UsersDAO(context);
    }

    public boolean addUser(User user) throws SQLiteException{

        return usersDAO.insertar(user);
    }

    public boolean updateUser(User users) throws SQLiteException{

        return usersDAO.alterar(users);
    }

    public boolean deleteUser(User users) throws SQLiteException{

        return usersDAO.borrar(users);
    }

   // public boolean getUser(User users) {
      //  return usersDAO.getUser(users);
    //}
    public boolean getUser(final String user, final String password) throws SQLiteException {
        boolean des=false;

            des= usersDAO.getUser(user, password);

        return des;
    }
   // public void listar(){
    //    usersDAO.listar();
    //}
}
