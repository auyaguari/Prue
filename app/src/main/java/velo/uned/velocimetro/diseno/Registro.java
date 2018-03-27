package velo.uned.velocimetro.diseno;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
    User users;
    UsersServicio usersServicio;
    TripleDES des;
    Validacion val;
    EditText nom,ap,us,pas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegistroBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_registro);
        nom=findViewById(R.id.txtNombre);
        ap=findViewById(R.id.txtapellido);
        us=findViewById(R.id.txtUsuarioReg);
        pas=findViewById(R.id.txtPassReg);
        users=new User();
        des=new TripleDES();
        val=new Validacion();
        usersServicio=new UsersServicio(this );
        binding.setDbuser(users);
    }

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
                            if (usersServicio.addUser(users, pass)) {
                                Toast.makeText(this, "Guardado Correctamente!", Toast.LENGTH_SHORT).show();
                                Intent intentIng = new Intent(Registro.this, Login.class);
                                Registro.this.startActivity(intentIng);
                            } else {
                                Toast.makeText(this, "Ocurrio Un error al guardar!", Toast.LENGTH_SHORT).show();
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


//valida que los campos esten llenos
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
    }