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
    // CONSTRUCTOR DE LA CLASE USERDAO
    public UsersDAO(Context context) {
        this.context = context;
        dbsqLite = new Conexion(context);
        des=new TripleDES();
    }
    // FUNCION PARA GUARDAR EL USER DE LA BASE DE DATOS
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
            values.put(user.campo_estado,"Activa");
            id = db.insert(User.tabla, null, values);
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
        }finally{
            db.close();
        }
        return id > 0;
    }
    public boolean alterar(User user,String pass) throws SQLiteException{
        int id=0;
        SQLiteDatabase db = null;
        try {
            db = dbsqLite.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(user.campo_nombre, user.getNombre());
            values.put(user.campo_apellido, user.getApellido());
            values.put(user.campo_rol, user.getRol());
            values.put(user.campo_usuario, user.getUser());
            values.put(user.campo_contraseña,pass);
            values.put(user.campo_estado,user.getEstado());
            String where = user.campo_id + " = ?";
            id = db.update(user.tabla, values, where, new String[]{String.valueOf(user.getId())});
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());

        }finally{
            db.close();
        }
        return id > 0;
    }
    // FUNCION PARA MODIFICAR EL USER DE LA BASE DE DATOS
    public boolean alterarIntento(User user) {
        SQLiteDatabase db =dbsqLite.getWritableDatabase();
        int id=0;
        try {
            Integer cont=user.getIntento();
            ContentValues values = new ContentValues();
            if (cont==3){
                values.put(user.campo_estado,"Bloqueada");
            }else{
                if (cont==0)
                    values.put(user.campo_estado,"Activa");
            }
            values.put(user.campo_intentos, cont);
            String where = user.campo_id + " = ?";
            id = db.update(user.tabla, values, where, new String[]{String.valueOf(user.getId())});
            db.close();
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
        }

        return id > 0;
    }
    // FUNCION PARA BORRAR EL USER DE LA BASE DE DATOS
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

    // FUNCION PARA OBTENER EL USER DE LA BASE DE DATOS BUSCANDO POR USUARIO ( LA FUNCION DEVUELVE UN OBJETO DE TIPO USER)
    public User getUser(User user){
        SQLiteDatabase db =dbsqLite.getReadableDatabase();
        User nuUser=new User();
        try {
            String [] campos={User.campo_id,User.campo_nombre,User.campo_apellido,User.campo_rol,
                    User.campo_usuario,User.campo_contraseña,User.campo_intentos,User.campo_estado};
            String [] parametro={user.getUser()};
            String where = User.campo_usuario + "=?";
            Cursor cursor = db.query(User.tabla,campos,where,parametro,null,null,null);
            if(cursor.moveToFirst()){
                nuUser.setId(cursor.getLong(0));
                nuUser.setNombre(cursor.getString(1));
                nuUser.setApellido(cursor.getString(2));
                nuUser.setRol(cursor.getString(3));
                nuUser.setUser(cursor.getString(4));
                nuUser.setPass(cursor.getString(5));
                nuUser.setIntento(cursor.getInt(6));
                nuUser.setEstado(cursor.getString(7));
                Log.v("pruebaData",String.valueOf(nuUser.getId()));
            }
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
        }
        return nuUser;
    }

    // FUNCION PARA MODIFICAR LA MEDICION EN LA BASE DE DATOS BUSCANDO POR ID
    public User getUserID(User user){
        SQLiteDatabase db =dbsqLite.getReadableDatabase();
        User nuUser=new User();
        try {
            String [] campos={User.campo_id,User.campo_nombre,User.campo_apellido,User.campo_rol,
                    User.campo_usuario,User.campo_contraseña,User.campo_intentos,User.campo_estado};
            String [] parametro={user.getId().toString()};
            String where = User.campo_id + "=?";
            Cursor cursor = db.query(User.tabla,campos,where,parametro,null,null,null);
            if(cursor.moveToFirst()){
                nuUser.setId(cursor.getLong(0));
                nuUser.setNombre(cursor.getString(1));
                nuUser.setApellido(cursor.getString(2));
                nuUser.setRol(cursor.getString(3));
                nuUser.setUser(cursor.getString(4));
                nuUser.setPass(cursor.getString(5));
                nuUser.setIntento(cursor.getInt(6));
                nuUser.setEstado(cursor.getString(7));
            }
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
        }
        return nuUser;
    }
    // FUNCION PARA OBTENER TODOS LOS USERS DE LA BASE DE DATOS ( DEVUELVE UN ARRAY DE TIPO USER)
    public ArrayList<User> listar() {
        SQLiteDatabase db = dbsqLite.getReadableDatabase();
        ArrayList<User> listuser = new ArrayList<>();
        try {
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
                    //Log.v("Funciona",nuUser.getId()+nuUser.getNombre()+nuUser.getApellido()+nuUser.getRol()+nuUser.getUser()+nuUser.getPass()
                    //+nuUser.getIntento()+nuUser.getEstado());
                    listuser.add(nuUser);
                } while (cursor.moveToNext());
                db.close();
            }
        }catch (Exception e){
            Log.d(this.getClass().toString(), e.getMessage());
        }

        return listuser;
    }
}
