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
    private MutableLiveData<Bundle> irAPagos = new MutableLiveData<>();
    private MutableLiveData<Bundle> irAInquilino = new MutableLiveData<>();
    private Application app;
    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<Contrato> getContrato() {
        return contrato;
    }

    public LiveData<Bundle> getNavegarAPagos() {
        return irAPagos;
    }

    public LiveData<Bundle> getNavegarAInquilino() {
        return irAInquilino;
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
                    contrato.postValue(response.body());
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
    public String formatoDireccion() {
        Contrato c = contrato.getValue();
        return "Dirección: " + ((c != null && c.getInmueble() != null)
                ? c.getInmueble().getDireccion()
                : "-");
    }

    public String formatoFechas() {
        Contrato c = contrato.getValue();
        return "Inicio: " + (c != null && c.getFechaInicio() != null ? c.getFechaInicio() : "-")
                + "  |  Fin: " + (c != null && c.getFechaFinalizacion() != null ? c.getFechaFinalizacion() : "-");
    }

    public String formatoInquilino() {
        Contrato c = contrato.getValue();
        return "Inquilino: " + ((c != null && c.getInquilino() != null)
                ? c.getInquilino().getNombre() + " " + c.getInquilino().getApellido()
                : "-");
    }

    public String formatoMonto() {
        Contrato c = contrato.getValue();
        return "Monto: " + ((c != null)
                ? "$" + c.getMontoAlquiler()
                : "-");
    }

    public String formatoEstado() {
        Contrato c = contrato.getValue();
        return "Estado: " + ((c != null)
                ? (c.isEstado() ? "Activo" : "Inactivo")
                : "-");
    }

    // Estos métodos serán llamados desde el Fragment (sin lógica allí)
    // El ViewModel decide si hay contrato y arma el Bundle para navegación
    public void onVerPagosClicked() {
        Contrato c = contrato.getValue();
        if (c == null) {
            Toast.makeText(app, "No hay contrato cargado para mostrar los pagos", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle b = new Bundle();
        b.putInt("idContrato", c.getIdContrato());
        irAPagos.postValue(b);
    }

    public void onVerInquilinoClicked() {
        Contrato c = contrato.getValue();
        if (c == null || c.getInquilino() == null) {
            Toast.makeText(app, "No hay inquilino disponible para este contrato", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle b = new Bundle();
        b.putSerializable("inquilino", c.getInquilino());//Mandar al inquilino completo
        irAInquilino.postValue(b);
    }

}