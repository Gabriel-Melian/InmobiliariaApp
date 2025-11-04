package com.lab3.inmobiliariapp.ui.pagos;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lab3.inmobiliariapp.models.Pago;
import com.lab3.inmobiliariapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> pagos = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private Application app;

    public PagoViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<List<Pago>> getPagos() {
        return pagos;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    //Recibe argumentso del Fragment
    public void recibirArgumentos(Bundle args) {
        if (args == null) {
            mensaje.postValue("No se enviaron datos para mostrar los pagos.");
            return;
        }
        int idContrato = args.getInt("idContrato", -1);
        if (idContrato != -1) {
            cargarPagosPorContrato(idContrato);
        } else {
            mensaje.postValue("ID de contrato inválido.");
        }
    }

    //Carga los pagos desde la API
    private void cargarPagosPorContrato(int idContrato) {
        String token = ApiClient.leerToken(app);
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();

        Call<List<Pago>> call = api.obtenerPagosPorContrato("Bearer " + token, idContrato);
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pagos.postValue(response.body());
                    if (response.body().isEmpty()) {
                        mensaje.postValue("No hay pagos registrados para este contrato.");
                    } else {
                        mensaje.postValue("");//No muestra nada si todo sale bien
                    }
                } else {
                    mensaje.postValue("Error al obtener los pagos del contrato.");
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                mensaje.postValue("Error de conexión: " + t.getMessage());
                Toast.makeText(app, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}