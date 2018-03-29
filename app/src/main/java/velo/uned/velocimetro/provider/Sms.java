package velo.uned.velocimetro.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Alvaro on 26/3/2018.
 */

public class Sms extends ContentProvider{


    private static final String uri =
            "content://velo.uned.proveedor.provider/sms";

    public static final Uri CONTENT_URI = Uri.parse(uri);


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Uri url = Uri.parse("content://sms/inbox");
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(url,null,null,null,null);


        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Uri url = Uri.parse("content://sms/inbox");
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(uri,null,null,null,null);


        return cursor.toString();
    }
    @Nullable

    public String getType(@NonNull Uri uri, Context cont) {
        Uri url = Uri.parse("content://sms/inbox");
        ContentResolver resolver = cont.getContentResolver();
        Cursor cursor = resolver.query(uri,null,null,null,null);


        return cursor.toString();
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
