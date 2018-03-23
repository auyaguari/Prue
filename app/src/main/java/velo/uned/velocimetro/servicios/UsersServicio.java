package velo.uned.velocimetro.servicios;

import android.content.Context;

import velo.uned.velocimetro.dao.UsersDAO;
import velo.uned.velocimetro.modelo.User;


/**
 * Created by alexa on 20/03/2018.
 */

public class UsersServicio {
    private UsersDAO usersDAO;

    public UsersServicio(Context context) {
        usersDAO = new UsersDAO(context);
    }

    public boolean addUser(User users) {

        return usersDAO.insertar(users);
    }

    public boolean updateUser(User users) {

        return usersDAO.alterar(users);
    }

    public boolean deleteUser(User users) {

        return usersDAO.borrar(users);
    }

    public boolean getUser(User users) {
        return usersDAO.getUser(users);
    }
    public boolean getUserO(User users) {
        return usersDAO.getUserO(users);
    }
}
