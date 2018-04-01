package velo.uned.velocimetro.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import velo.uned.velocimetro.R;
import velo.uned.velocimetro.modelo.Ruta;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.servicios.RutaServicios;
import velo.uned.velocimetro.servicios.UsersServicio;

/**
 * Created by Alvaro on 28/03/2018.
 */

public class AdaptadorListRuta extends BaseAdapter {
    LayoutInflater layoutInflater;
    RutaServicios rutaServicios;
    // CONSTRUCTOR
    public AdaptadorListRuta(Context context,Long id) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rutaServicios = new RutaServicios(context);
        rutaServicios.getallRutaId(id);
    }

    // RETORNA EL TAMAÑO DE LA LISTA DE USUARIOS
    @Override
    public int getCount() {
        return rutaServicios.rutalist.size();
    }
    // RETORNA EL ITEM DE LA LISTA USER SEGUN LA POSICION
    @Override
    public Object getItem(int position) {
        return rutaServicios.rutalist.get(position);
    }
    // RETORNA EL ID DEL ITEM DE LA LISTA USER SEGUN LA POSICION
    @Override
    public long getItemId(int position) {
        Ruta nuRuta = rutaServicios.rutalist.get(position);
        return nuRuta.getId();
    }
    // FUNCIONA PARA AGREGAR LOS ITEMS A LA LIST VIEW
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_lista_ruta, null);
        }
        TextView tvlongitud = convertView.findViewById(R.id.tvlongitud);
        TextView tvlatitud = convertView.findViewById(R.id.tvlatitud);
        Ruta nuRuta = rutaServicios.rutalist.get(position);

        tvlongitud.setText(nuRuta.getLatitud().toString());
        tvlatitud.setText(nuRuta.getLongitud().toString());
        return convertView;
    }
}
