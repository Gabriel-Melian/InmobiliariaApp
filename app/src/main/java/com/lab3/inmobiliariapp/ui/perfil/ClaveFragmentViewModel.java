package com.lab3.inmobiliariapp.ui.perfil;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lab3.inmobiliariapp.request.ApiClient;
import com.lab3.inmobiliariapp.ui.home.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClaveFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private Application app;

    public ClaveFragmentViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void cambiarClave(String claveActual, String claveNueva) {
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
        String token = ApiClient.leerToken(app);

        Call<Void> call = api.changePassword("Bearer " + token, claveActual, claveNueva);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.postValue("Clave actualizada correctamente");
                } else {
                    mensaje.postValue("Error al actualizar la clave");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensaje.postValue("Error al contactar con el servidor");
            }
        });

    }

}