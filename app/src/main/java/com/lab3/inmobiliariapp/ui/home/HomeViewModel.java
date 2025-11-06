package com.lab3.inmobiliariapp.ui.home;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<GoogleMap> mapaLiveData = new MutableLiveData<>();
    private Application app;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<GoogleMap> getMapa() {
        return mapaLiveData;
    }

    //Recibe el mapa cuando esta listo desde el fragment
    public void setMapa(GoogleMap googleMap) {
        if (googleMap == null) {
            Toast.makeText(app, "Error al cargar el mapa", Toast.LENGTH_SHORT).show();
            return;
        }

        this.mapaLiveData.setValue(googleMap);
        configurarMapa(googleMap);
    }

    //Configura el mapa con marcador, zoom y ubicacion
    private void configurarMapa(GoogleMap googleMap) {
        try {
            //Coordenadas de San Luis
            LatLng ubicacion = new LatLng(-33.301726, -66.337752);

            //Configurar marcador
            MarkerOptions marcador = new MarkerOptions()
                    .position(ubicacion)
                    .title("Inmobiliaria Melian")
                    .snippet("Sucursal Zona Centro");

            googleMap.addMarker(marcador);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));

        } catch (Exception e) {
            Toast.makeText(app, "Error al inicializar el mapa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}