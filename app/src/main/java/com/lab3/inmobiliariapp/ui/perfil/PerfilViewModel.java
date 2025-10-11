package com.lab3.inmobiliariapp.ui.perfil;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lab3.inmobiliariapp.models.PropietarioModel;
import com.lab3.inmobiliariapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<PropietarioModel> propietario = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private Application app;

    public PerfilViewModel(Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<PropietarioModel> getPropietario() {
        return propietario;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void cargarPropietario() {
        ApiClient.InmoService apiClient = ApiClient.getApiInmobiliaria();
        String token = ApiClient.leerToken(app);

        if (token == null || token.isEmpty()) {
            mensaje.postValue("Token no encontrado. Inicie sesión nuevamente.");
            return;
        }

        Call<PropietarioModel> call = apiClient.getPropietario("Bearer " + token);
        //La API espera "Bearer " + token

        call.enqueue(new Callback<PropietarioModel>() {
            @Override
            public void onResponse(Call<PropietarioModel> call, Response<PropietarioModel> response) {
                if (response.isSuccessful()) {
                    propietario.postValue(response.body());
                } else {
                    mensaje.postValue("Error al cargar el propietario.");
                }
            }

            @Override
            public void onFailure(Call<PropietarioModel> call, Throwable t) {
                mensaje.postValue("Error al contactar con el servidor.");
            }
        });
    }
}