package velo.uned.velocimetro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import velo.uned.velocimetro.datos.Conexion;
import velo.uned.velocimetro.modelo.Medicion;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.util.TripleDES;
//import velo.uned.velocimetro.modelo.Users;

/**
 * Created by alexa on 20/03/2018.
 */

public class UsersDAO {
    Context context;
    Conexion dbsqLite;
    TripleDES des;
    public static ArrayList<User> listaUsers;

    public UsersDAO(Context context) {
        this.context = context;
        dbsqLite = new Conexion(context);
        des=new TripleDES();
    }

    public boolean insertar(User user,String pass) throws SQLiteException{
        Long id = null;
        SQLiteDatabase db = null;
        try {
            db = dbsqLite.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(user.campo_nombre, user.getNombre());
            values.put(user.campo_apellido, user.getApellido());
            values.put(user.campo_rol, user.getRol());
            values.put(user.campo_usuario, user.getUser());
            values.put(user.campo_contraseña,pass);
            id = db.insert(User.tabla, null, values);
        }catch (SQLiteException ex){
            throw ex;

        }finally{
            db.close();
        }




        return id > 0;
    }
    public boolean alterar(User user) {
        SQLiteDatabase db = dbsqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user.campo_usuario, user.getUser());
        values.put(user.campo_contraseña, user.getPass());
        String where = user.campo_id + " = ?";

        int id = db.update(user.tabla, values, where, new String[]{String.valueOf(user.getId())});
        db.close();
        return id > 0;
    }


    public boolean borrar(User user ) throws SQLiteException{
        SQLiteDatabase db = null;
        int ret = 0;
        try {
        db = dbsqLite.getWritableDatabase();
        String where = user.campo_id + " = ?";
        ret = db.delete(user.tabla, where, new String[]{String.valueOf(user.getId())});
    }catch (SQLiteException ex) {
        throw ex;
    }finally {
        db.close();

    }
        return ret > 0;
    }
    public Boolean getUser(User user){
        SQLiteDatabase db =dbsqLite.getReadableDatabase();
        String [] campos={user.campo_usuario,user.campo_contraseña};
        String [] parametro={user.getUser().toString(),user.getPass().toString()};
        String where = user.campo_usuario + " = ?"+" AND "+user.campo_contraseña+" = ?";
        Cursor cursor = db.query(user.tabla,campos,where,parametro,null,null,null);
        return cursor!=null;
    }
    public Boolean getUser(final String user, final String password) throws SQLiteException {
        SQLiteDatabase db =dbsqLite.getReadableDatabase();
        boolean dess=false;
        try {
            String [] campos={User.campo_usuario,User.campo_contraseña};
            String [] parametro={user};
            String where = User.campo_usuario + "=?";
            Cursor cursor = db.query(User.tabla,campos,where,parametro,null,null,null);
            cursor.moveToFirst();
            if (des.decrypt(cursor.getString(1)).equals(des.decrypt(password)))
                dess= true;
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
            throw new SQLiteException("Error en la ecriptación" + e.getMessage());
        }
            return dess;
    }
    public void listar() {
        SQLiteDatabase db = dbsqLite.getReadableDatabase();
        ArrayList<User> listuser = new ArrayList<>();

        String selectQuery = "SELECT  " +
                User.campo_id + "," +
                User.campo_nombre + "," +
                User.campo_apellido + "," +
                User.campo_rol + "," +
                User.campo_usuario + "," +
                User.campo_contraseña +
                " FROM " + User.tabla;

        Cursor cursor = db.rawQuery(selectQuery, null);
        User nuUser;

        if (cursor.moveToFirst()) {
            do {
                nuUser = new User();
                nuUser.setId(cursor.getLong(0));
                nuUser.setNombre(cursor.getString(1));
                nuUser.setApellido(cursor.getString(2));
                nuUser.setRol(cursor.getString(3));
                nuUser.setUser(cursor.getString(4));
                nuUser.setPass(cursor.getString(5));
                listuser.add(nuUser);
                Log.v("FUnciona",nuUser.getId()+nuUser.getApellido()+nuUser.getNombre()+nuUser.getRol()+nuUser.getUser()+nuUser.getPass());

            } while (cursor.moveToNext());
            db.close();
        }
    }
}
