package velo.uned.velocimetro.diseno;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import velo.uned.velocimetro.R;
import velo.uned.velocimetro.util.AdaptadorListMedicion;
import velo.uned.velocimetro.util.AdaptadorListUser;

public class ListaMediciones extends AppCompatActivity {
    private AdaptadorListMedicion adaptadorListMedicion;
    private ListView listView;
    private long id;
    //CREA LA ACTIVIDAD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mediciones);
        final Context context = this;
        id=getIntent().getLongExtra("id",0);
        listView = findViewById(R.id.listvmedicion);
        adaptadorListMedicion = new AdaptadorListMedicion(this,id);
        listView.setAdapter(adaptadorListMedicion);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,ListaRuta.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
    // ACTUALIZA EL ADAPTADOR DE LA LISTVIEW CUANDO LA ACTIVIDAD ESTA RESUMEN
    @Override
    public void onResume(){
        super.onResume();
        adaptadorListMedicion.notifyDataSetChanged();
    }

    public void salir(View view) {
        finish();
    }
}
