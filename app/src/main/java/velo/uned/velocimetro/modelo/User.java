package velo.uned.velocimetro.modelo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;



import velo.uned.velocimetro.BR;

/**
 * Created by alexa on 20/03/2018.
 */

public class User extends BaseObservable  {
    public static final String tabla="usuarios";
    public static final String campo_id="id";
    public static final String campo_contraseña="user";
    public static final String campo_usuario="password";
    public static final String campo_nombre="name";
    public static final String campo_apellido="last_name";
    public static final String campo_rol="rol";
    public static final String campo_intentos="intento";
    public static final String campo_estado="estado";

    private Long id;
    private String user;
    private String pass;
    private String nombre;
    private String apellido;
    private Integer intento;
    private String estado;
    //admin = Administrador
    //user = Usuario
    private String rol;

    public User() {


    }
    @Bindable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {

        this.id = id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getUser() {
        return user;
    }

    public void setUser(String user) {

        this.user = user;
        notifyPropertyChanged(BR.user);
    }
    @Bindable
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {

        this.pass = pass;
        notifyPropertyChanged(BR.pass);
    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(BR.nombre);
    }

    @Bindable
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
        notifyPropertyChanged(BR.apellido);
    }

    @Bindable
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
        notifyPropertyChanged(BR.rol);
    }

    @Bindable
    public Integer getIntento() {
        return intento;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
        notifyPropertyChanged(BR.intento);
    }

    @Bindable
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        notifyPropertyChanged(BR.estado);
    }
}

