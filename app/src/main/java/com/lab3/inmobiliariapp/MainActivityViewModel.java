package com.lab3.inmobiliariapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lab3.inmobiliariapp.models.PropietarioModel;
import com.lab3.inmobiliariapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<PropietarioModel> propietario = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        cargarPropietario();
    }

    public LiveData<PropietarioModel> getPropietario() {
        return propietario;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    private void cargarPropietario() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
        Call<PropietarioModel> call = api.getPropietario("Bearer " + token);

        call.enqueue(new Callback<PropietarioModel>() {
            @Override
            public void onResponse(Call<PropietarioModel> call, Response<PropietarioModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    propietario.postValue(response.body());
                } else {
                    mensaje.postValue("Error al cargar los datos del propietario.");
                }
            }

            @Override
            public void onFailure(Call<PropietarioModel> call, Throwable t) {
                mensaje.postValue("Error al contactar con el servidor.");
            }
        });
    }
}
