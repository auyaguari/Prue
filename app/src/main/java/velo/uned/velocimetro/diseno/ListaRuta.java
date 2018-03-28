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
import velo.uned.velocimetro.util.AdaptadorListRuta;

public class ListaRuta extends AppCompatActivity {
    private AdaptadorListRuta adaptadorListRuta;
    private ListView listView;
    private Long id;
    //CREA LA ACTIVIDAD
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ruta);
        final Context context = this;
        id=getIntent().getLongExtra("id",0);
        listView = findViewById(R.id.listvruta);
        adaptadorListRuta = new AdaptadorListRuta(this,id);
        listView.setAdapter(adaptadorListRuta);
    }
    // ACTUALIZA EL ADAPTADOR DE LA LISTVIEW CUANDO LA ACTIVIDAD ESTA EN REPOSO O RESUMEN
    @Override
    public void onResume(){
        super.onResume();
        adaptadorListRuta.notifyDataSetChanged();
    }

    public void salir(View view) {
        finish();
    }
}