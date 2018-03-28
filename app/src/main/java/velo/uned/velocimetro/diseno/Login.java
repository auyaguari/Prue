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
    //CREA LA ACTIVIDAD
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
//FUNCION PARA VALIDAR LOS ATRIBUTOS CON LA CLASE VALIDACION Y INGRESO AL SISTEMA
    public void ingresar(View view){
        Log.v("pruebaData5",String.valueOf(user.getId()));
        if(validar()){
            if (val.validarCaracteresEspeciales(user.getUser())){
                if (val.validarCaracteresEspeciales(user.getPass())){
                    TripleDES des = new TripleDES();
                    String dess=null;
                    try {
                        dess=des.encrypt(user.getPass());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (usersServicio.validarLogin(user, dess)){
                                usersServicio.updateNuevo(user);
                                Toast.makeText(this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                                Intent intentIng = new Intent(Login.this, ActividadPrincipal.class);
                                intentIng.putExtra("id",user.getId());
                                Login.this.startActivity(intentIng);

                        } else {
                            usersServicio.updateIntento(user);
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
                    pass.setError(getString(R.string.errorCaracter));
                }

            }else{
                Toast.makeText(this, "User Incorrecto!", Toast.LENGTH_SHORT).show();
                us.requestFocus();
                user.setUser(null);
                pass.setError(getString(R.string.errorCaracter));
            }
        }
    }
    //FUNCION PARA VALIDAR QUE LOS ATRIBUTOS ESTEN LLENOS
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
