package velo.uned.velocimetro.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import velo.uned.velocimetro.R;
import velo.uned.velocimetro.modelo.User;
import velo.uned.velocimetro.servicios.UsersServicio;

/**
 * Created by alexa on 27/03/2018.
 */

public class AdaptadorListUser extends BaseAdapter {
    LayoutInflater layoutInflater;
    UsersServicio usersServicio;

    public AdaptadorListUser(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        usersServicio = new UsersServicio(context);
    }


    @Override
    public int getCount() {
        return usersServicio.listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return usersServicio.listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        User nuUser = usersServicio.listUser.get(position);
        return nuUser.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_lista_user, null);
        }

        TextView tvnombre = convertView.findViewById(R.id.tvnombre);
        TextView tvapellido = convertView.findViewById(R.id.tvapellido);
        User nuUser = usersServicio.listUser.get(position);

        tvnombre.setText(nuUser.getNombre());
        tvapellido.setText(nuUser.getApellido());
        return convertView;
    }
}
