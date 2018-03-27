package velo.uned.velocimetro.diseno;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import velo.uned.velocimetro.databinding.ActivityLoginBinding;
import velo.uned.velocimetro.main.ActividadPrincipal;
import velo.uned.velocimetro.modelo.User;

import velo.uned.velocimetro.servicios.UsersServicio;
import velo.uned.velocimetro.R;
import velo.uned.velocimetro.util.TripleDES;
import velo.uned.velocimetro.util.Validacion;

public class Login extends AppCompatActivity {
    User user;
    Validacion val;
    EditText us,pass;
    UsersServicio usersServicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
        }
        //ActivityActividadPrincipalBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_actividad_principal);
        //med = new Medicion();
        //binding.setDbMedicion(data);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        val=new Validacion();
        user =new User();
        usersServicio=new UsersServicio(this);
        binding.setDbuser(user);
        us=findViewById(R.id.txtUsuario);
        pass=findViewById(R.id.txtPass);

        //btnreg=findViewById(R.id.btnRegistrar);
        //btnreg.setOnClickListener(new View.OnClickListener() {

        //    @Override
        //    public void onClick(View view) {
        //        Intent intentReg = new Intent(Login.this,Registro.class);
        //        Login.this.startActivity(intentReg);
        //    }
        //});
    }
//Ingresar Al sistema y validacion de datos
    public void ingresar(View view) throws Exception {
        if(validar()){
            if (val.validarCaracteresEspeciales(user.getUser())){
                if (val.validarPassword(user.getPass())){
                    TripleDES des = new TripleDES();
                    String dess=null;
                    try {
                        dess=des.encrypt(user.getPass());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (usersServicio.getUser(user.getUser(), dess)){
                            Toast.makeText(this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                            Intent intentIng = new Intent(Login.this, ActividadPrincipal.class);
                            Login.this.startActivity(intentIng);
                        } else {
                            Toast.makeText(this, "Logeo Incorrecto!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d(this.getClass().toString(), e.getMessage());
                        Toast.makeText(this, "Logeo Incorrecto!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Password Incorrecto!", Toast.LENGTH_SHORT).show();
                    pass.requestFocus();
                    user.setPass(null);
                    pass.setError(getString(R.string.errorpassword));
                }

            }else{
                Toast.makeText(this, "User Incorrecto!", Toast.LENGTH_SHORT).show();
                us.requestFocus();
                user.setUser(null);
            }
        }
    }
    //valida que los campos esten llenos
    public boolean validar(){
        boolean des=true;
        if (TextUtils.isEmpty(user.getUser())){
            us.setError(getString(R.string.errorCampoObligatorio));
            us.requestFocus();
            des=false;
        }
        if (TextUtils.isEmpty(user.getPass())){
            pass.setError(getString(R.string.errorCampoObligatorio));
            pass.requestFocus();
            des=false;
        }
        return des;
    }
}
