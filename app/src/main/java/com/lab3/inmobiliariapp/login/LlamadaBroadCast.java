package com.lab3.inmobiliariapp.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class LlamadaBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean deteccionAgitado = intent.getBooleanExtra("deteccion_agitado", false);
        if (deteccionAgitado) {
            realizarLlamada(context);
        }
    }

    private void realizarLlamada(Context context) {
        Intent intentLlamada = new Intent(Intent.ACTION_CALL);
        intentLlamada.setData(Uri.parse("tel:2664680489"));//Mi numero para testeo
        intentLlamada.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intentLlamada);
        } else {
            Toast.makeText(context, "Permiso denegado para realizar llamadas", Toast.LENGTH_SHORT).show();
        }
    }
}
