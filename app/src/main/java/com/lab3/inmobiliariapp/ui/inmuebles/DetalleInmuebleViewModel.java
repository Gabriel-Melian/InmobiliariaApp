package com.lab3.inmobiliariapp.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lab3.inmobiliariapp.models.InmuebleModel;
import com.lab3.inmobiliariapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<InmuebleModel> inmueble = new MutableLiveData<>();

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<InmuebleModel> getInmueble(){
        return inmueble;
    }

    public void obtenerInmueble(Bundle inmuebleBundle){
        InmuebleModel inmueble = (InmuebleModel) inmuebleBundle.getSerializable("inmueble");

        if(inmueble != null){
            this.inmueble.setValue(inmueble);
        }

    }

    public void actualizarInmueble(Boolean disponible){
        InmuebleModel inmueble = new InmuebleModel();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(this.inmueble.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Call<InmuebleModel> llamada = ApiClient.getApiInmobiliaria().actualizarInmueble("Bearer " + token, inmueble);
        llamada.enqueue(new Callback<InmuebleModel>() {
            @Override
            public void onResponse(Call<InmuebleModel> call, Response<InmuebleModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Inmueble actualizado correctamente", Toast.LENGTH_SHORT).show();
                    //inmueble.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al actualizar el inmueble: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InmuebleModel> call, Throwable t) {
                Toast.makeText(getApplication(), "Error al contactar con el servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}