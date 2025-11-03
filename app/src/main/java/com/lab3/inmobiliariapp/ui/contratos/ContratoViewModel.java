package com.lab3.inmobiliariapp.ui.contratos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lab3.inmobiliariapp.models.InmuebleModel;
import com.lab3.inmobiliariapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {

    private MutableLiveData<List<InmuebleModel>> inmueblesVigentes = new MutableLiveData<>();
    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<InmuebleModel>> getInmueblesVigentes() {
        return inmueblesVigentes;
    }

    //Llama al endpoint para obtener los inmuebles con contrato vigente
    public void cargarInmueblesConContratoVigente() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();

        Call<List<InmuebleModel>> call = api.obtenerInmueblesConContratoVigente("Bearer " + token);
        call.enqueue(new Callback<List<InmuebleModel>>() {
            @Override
            public void onResponse(Call<List<InmuebleModel>> call, Response<List<InmuebleModel>> response) {
                if (response.isSuccessful()) {
                    inmueblesVigentes.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener los inmuebles con contrato vigente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<InmuebleModel>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}