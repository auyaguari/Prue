package velo.uned.velocimetro.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import velo.uned.velocimetro.R;
import velo.uned.velocimetro.modelo.Medicion;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.servicios.MedicionServicio;
import velo.uned.velocimetro.servicios.UsersServicio;

/**
 * Created by alexa on 28/03/2018.
 */

public class AdaptadorListMedicion extends BaseAdapter {
    LayoutInflater layoutInflater;
    MedicionServicio medicionServicio;
    // CONSTRUCTOR
    public AdaptadorListMedicion(Context context,long id) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        medicionServicio = new MedicionServicio(context);
        medicionServicio.getallMedicion(id);
    }

    // RETORNA EL TAMAÑO DE LA LISTA DE Mediciones
    @Override
    public int getCount() {
        return medicionServicio. listMedicion.size();
    }
    // RETORNA EL ITEM DE LA LISTA USER SEGUN LA POSICION
    @Override
    public Object getItem(int position) {
        return medicionServicio.listMedicion.get(position);
    }
    // RETORNA EL ID DEL ITEM DE LA LISTA USER SEGUN LA POSICION
    @Override
    public long getItemId(int position) {
        Medicion nuMedicion = medicionServicio.listMedicion.get(position);
        return nuMedicion.getId();
    }
    // FUNCIONA PARA AGREGAR LOS ITEMS A LA LIST VIEW
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_list_mediciones, null);
        }

        TextView tvdistancia = convertView.findViewById(R.id.tvdistancia);
        Medicion nuMedicion = medicionServicio.listMedicion.get(position);

        tvdistancia.setText(String.valueOf(nuMedicion.getDistancia()));
        return convertView;
    }
}
