package velo.uned.velocimetro.modelo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;



import velo.uned.velocimetro.BR;

/**
 * Created by alexa on 20/03/2018.
 */

public class User extends BaseObservable  {
    public static final String tabla="usuarios";
    public static final String campo_id="id_rut";
    public static final String campo_contraseña="latitud_rut";
    public static final String campo_usuario="longitud_rut";

    private Long id;
    private String user;
    private String pass;
    public User() {


    }
    @Bindable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Bindable
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    @Bindable
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


}

