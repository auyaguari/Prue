package velo.uned.velocimetro.diseno;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import velo.uned.velocimetro.databinding.ActivityRegistroBinding;
import velo.uned.velocimetro.main.ActividadPrincipal;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.servicios.UsersServicio;
import velo.uned.velocimetro.R;
import velo.uned.velocimetro.util.TripleDES;
import velo.uned.velocimetro.util.Validacion;

public class Registro extends AppCompatActivity {
    String operacion;
    int posicion;
    Long id;
    User users;
    UsersServicio usersServicio;
    TripleDES des;
    Validacion val;
    EditText nom,ap,us,pas;
    Button eliminar,actualizar,desbloquear;
    //CREA LA ACTIVIDAD PRINCIPAL
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegistroBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_registro);
        nom=findViewById(R.id.txtNombre);
        ap=findViewById(R.id.txtapellido);
        us=findViewById(R.id.txtUsuarioReg);
        pas=findViewById(R.id.txtPassReg);
        operacion = getIntent().getStringExtra("operacion");
        posicion = getIntent().getIntExtra("posicion", 0);
        id = getIntent().getLongExtra("id",0);
        eliminar=findViewById(R.id.btnEliminar);
        actualizar=findViewById(R.id.btnRegistarReg);
        desbloquear=findViewById(R.id.btnLiberar);
        users=new User();
        des=new TripleDES();
        val=new Validacion();
        usersServicio=new UsersServicio(this );
        if (operacion.equals("alterar")){
            eliminar.setVisibility(View.VISIBLE);
            desbloquear.setVisibility(View.VISIBLE);
            users.setId(id);
            users=usersServicio.getUser(users);
            try {
                users.setPass(des.decrypt(users.getPass()));
                if (users.getUser().equals("admin")){
                    us.setEnabled(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        binding.setDbuser(users);
    }
// FUNCION PARA VALIDAR LOS ATRIBUTOS CON AYUDA DE LA CLASE VALIDACION Y GUARDA UN USUARIO NUEVO O LO MODIFICA
    public void registrar(View view) throws Exception {
        String pass = null;
        if (validar()) {
            if (val.validarCaracteresEspeciales(users.getNombre()) & val.validarLongitud(users.getNombre())) {
                if (val.validarCaracteresEspeciales(users.getApellido()) & val.validarLongitud(users.getApellido())) {
                    if (val.validarCaracteresEspeciales(users.getUser())) {
                        if (val.validarPassword(users.getPass())) {
                            try {
                                pass = des.encrypt(users.getPass());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (operacion.equals("alterar")){
                                if (usersServicio.update(users, pass)) {
                                    Toast.makeText(this, "Guardado Correctamente!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(this, "Ocurrio Un error al guardar!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                if (usersServicio.addUser(users, pass)) {
                                    Toast.makeText(this, "Guardado Correctamente!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(this, "Ocurrio Un error al guardar!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            pas.setError(getString(R.string.errorpassword));
                            pas.requestFocus();
                            users.setPass(null);
                        }
                    } else {
                        us.setError(getString(R.string.errorCaracter));
                        us.requestFocus();
                        users.setUser(null);
                    }
                } else {
                    ap.setError(getString(R.string.errorcampo));
                    ap.requestFocus();
                    users.setApellido(null);
                }
            } else {
                nom.setError(getString(R.string.errorcampo));
                nom.requestFocus();
                users.setNombre(null);
            }
        }
    }


//FUNCION PARA VALIDAR QUE LOS CAMPOS ESTEN LLENOS
        public boolean validar(){
            boolean des = true;
            if (TextUtils.isEmpty(users.getNombre())) {
                nom.setError(getString(R.string.errorCampoObligatorio));
                nom.requestFocus();
                des = false;
            }
            if (TextUtils.isEmpty(users.getApellido())) {
                ap.setError(getString(R.string.errorCampoObligatorio));
                ap.requestFocus();
                des = false;
            }
            if (TextUtils.isEmpty(users.getUser())) {
                us.setError(getString(R.string.errorCampoObligatorio));
                us.requestFocus();
                des = false;
            }
            if (TextUtils.isEmpty(users.getPass())) {
                pas.setError(getString(R.string.errorCampoObligatorio));
                pas.requestFocus();
                des = false;
            }
            return des;
        }
//quitar el bloqueo del usuario
    public void liberar(View view) {
    }

    public void eliminar(View view) {
    }
}