package velo.uned.velocimetro.diseno;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import velo.uned.velocimetro.databinding.ActivityRegistroBinding;
import velo.uned.velocimetro.main.ActividadPrincipal;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.servicios.UsersServicio;
import velo.uned.velocimetro.R;
import velo.uned.velocimetro.util.TripleDES;

public class Registro extends AppCompatActivity {
    User users;
    UsersServicio usersServicio;
    TripleDES des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegistroBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_registro);
        users=new User();
        des=new TripleDES();
        usersServicio=new UsersServicio(this );
        binding.setDbuser(users);
    }

    public void registrar(View view) throws Exception {
        users.setRol("Usuario");
        String pass;
        try {
            pass=des.encrypt(users.getPass());
            users.setPass(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (usersServicio.addUser(users)){
                Toast.makeText(this, "Guardado Correctamente!", Toast.LENGTH_SHORT).show();
                Intent intentIng = new Intent(Registro.this, Login.class);
                Registro.this.startActivity(intentIng);
        } else {
            Toast.makeText(this, "Ocurrio Un error al guardar!", Toast.LENGTH_SHORT).show();
        }

    }
}
