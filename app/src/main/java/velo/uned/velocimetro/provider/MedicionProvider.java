package velo.uned.velocimetro.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import velo.uned.velocimetro.servicios.MedicionServicio;

/**
 * Created by Alvaro on 26/3/2018.
 */

public class MedicionProvider extends ContentProvider {

    //Definición del CONTENT_URI
    private static final String uri =
            "content://velo.uned.velocimetro.provider/mediciones";

    public static final Uri CONTENT_URI = Uri.parse(uri);

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        MedicionServicio medicionServicio = new MedicionServicio(getContext());

        return medicionServicio.listarCursor();
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
