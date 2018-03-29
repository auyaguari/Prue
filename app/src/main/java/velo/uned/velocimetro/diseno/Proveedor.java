package velo.uned.velocimetro.diseno;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import velo.uned.velocimetro.R;

public class Proveedor extends AppCompatActivity {
    private final static String APP_NAME = "APP_NAME";
    private final static int REQUEST_READ_SMS_PERMISSION = 3004;
    public final static String READ_SMS_PERMISSION_NOT_GRANTED = "Please allow " + APP_NAME + " to access your SMS from setting";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor);
        Uri url = Uri.parse("content://sms/inbox");
        ContentResolver resolver = getContentResolver();
        // Should we show an explanation?
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                PackageManager.PERMISSION_GRANTED){
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    REQUEST_READ_SMS_PERMISSION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        Cursor cursor = resolver.query(url,null,null,null,null);
        int val =8;
    }
}
