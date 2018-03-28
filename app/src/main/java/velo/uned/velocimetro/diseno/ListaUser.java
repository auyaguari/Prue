package velo.uned.velocimetro.diseno;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import velo.uned.velocimetro.R;
import velo.uned.velocimetro.util.AdaptadorListUser;

public class ListaUser extends AppCompatActivity {
    AdaptadorListUser adaptadorListUser;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_user);
        final Context context = this;
        listView = findViewById(R.id.listuser);
        adaptadorListUser = new AdaptadorListUser(this);
        listView.setAdapter(adaptadorListUser);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,Registro.class);
                intent.putExtra("operacion", "alterar");
                intent.putExtra("posicion", position);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        adaptadorListUser.notifyDataSetChanged();
    }
}
