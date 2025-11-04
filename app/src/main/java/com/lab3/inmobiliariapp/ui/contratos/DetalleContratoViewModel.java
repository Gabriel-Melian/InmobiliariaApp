package com.lab3.inmobiliariapp.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lab3.inmobiliariapp.models.Contrato;
import com.lab3.inmobiliariapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> contrato = new MutableLiveData<>();
    private Application app;
    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<Contrato> getContrato() {
        return contrato;
    }

    public void recibirArgumentos(Bundle args) {
        if (args == null) {
            Toast.makeText(app, "No se enviaron argumentos al detalle del contrato", Toast.LENGTH_SHORT).show();
            return;
        }
        int idInmueble = args.getInt("idInmueble", -1);
        if (idInmueble == -1) {
            Toast.makeText(app, "ID de inmueble inválido", Toast.LENGTH_SHORT).show();
            return;
        }
        cargarContratoPorInmueble(idInmueble);
    }

    //Carga el contrato correspondiente al inmueble y publica en el LiveData
    public void cargarContratoPorInmueble(int idInmueble) {
        String token = ApiClient.leerToken(app);
        ApiClient.InmoService api = ApiClient.getApiInmobiliaria();
        Call<Contrato> call = api.obtenerContratoPorInmuebleId("Bearer " + token, idInmueble);
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Contrato c = response.body();
                    //Verificaciones
                    if (c.getInmueble() != null && (c.getInmueble().getDireccion() == null || c.getInmueble().getDireccion().isEmpty())) {
                        c.getInmueble().setDireccion("Sin dirección");
                    }
                    if (c.getInquilino() != null) {
                        if (c.getInquilino().getNombre() == null) c.getInquilino().setNombre("-");
                        if (c.getInquilino().getApellido() == null) c.getInquilino().setApellido("-");
                    }
                    if (c.getFechaInicio() == null) c.setFechaInicio("-");
                    if (c.getFechaFinalizacion() == null) c.setFechaFinalizacion("-");
                    //Post al contrato listo para mostrar
                    contrato.postValue(c);
                } else {
                    Toast.makeText(app, "Contrato no encontrado para ese inmueble", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(app, "Error de red al obtener contrato: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Formatear textos (el fragment solo invoca el LiveData del contrato y estos helpers)
    //Metodos auxiliares para mostrar los datos
    public String obtenerDireccion() {
        Contrato c = contrato.getValue();
        return (c != null && c.getInmueble() != null) ? c.getInmueble().getDireccion() : "-";
    }

    public String obtenerFechas() {
        Contrato c = contrato.getValue();
        if (c == null) return "Inicio: - | Fin: -";
        return "Inicio: " + c.getFechaInicio() + " | Fin: " + c.getFechaFinalizacion();
    }

    public String obtenerInquilino() {
        Contrato c = contrato.getValue();
        if (c == null || c.getInquilino() == null) return "Inquilino: -";
        return c.getInquilino().getNombre() + " " + c.getInquilino().getApellido();
    }

    public String obtenerMonto() {
        Contrato c = contrato.getValue();
        return (c != null) ? "$" + c.getMontoAlquiler() : "-";
    }

    public String obtenerEstado() {
        Contrato c = contrato.getValue();
        if (c == null) return "-";
        return c.isEstado() ? "Activo" : "Inactivo";
    }

    // Estos métodos serán llamados desde el Fragment
    // El ViewModel decide si hay contrato y arma el Bundle para navegation


}