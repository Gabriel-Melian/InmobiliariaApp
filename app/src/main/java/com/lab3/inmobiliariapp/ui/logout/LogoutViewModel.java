package com.lab3.inmobiliariapp.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lab3.inmobiliariapp.login.LoginActivity;
import com.lab3.inmobiliariapp.request.ApiClient;

public class LogoutViewModel extends AndroidViewModel {

    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private Context context;
    public LogoutViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void logout() {
        //Le seteo null al token para que cierre sesion.
        ApiClient.guardarToken(context, null);

        mensaje.postValue("Sesion cerrada correctamente.");

        //Intent para volver al LoginActivity
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
