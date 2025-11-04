package com.lab3.inmobiliariapp.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lab3.inmobiliariapp.models.Inquilino;

public class InquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> inquilino = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getInquilino() {
        return inquilino;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    //Recibe el objeto inquilino desde el bundle
    public void recibirArgumentos(Bundle args) {
        if (args == null || !args.containsKey("inquilino")) {
            mensaje.postValue("No se recibieron datos del inquilino.");
            return;
        }

        Object obj = args.getSerializable("inquilino");
        if (obj instanceof Inquilino) {
            inquilino.postValue((Inquilino) obj);
            mensaje.postValue(""); // limpiar mensaje si todo va bien
        } else {
            mensaje.postValue("Datos de inquilino inválidos.");
        }
    }

    //Formatear datos
    public String formatoNombre() {
        Inquilino i = inquilino.getValue();
        return (i == null) ? "Nombre: -" : "Nombre: " + i.getNombre() + " " + i.getApellido();
    }

    public String formatoDni() {
        Inquilino i = inquilino.getValue();
        return (i == null) ? "DNI: -" : "DNI: " + i.getDni();
    }

    public String formatoTelefono() {
        Inquilino i = inquilino.getValue();
        return (i == null) ? "Teléfono: -" : "Teléfono: " + i.getTelefono();
    }

    public String formatoEmail() {
        Inquilino i = inquilino.getValue();
        return (i == null) ? "Email: -" : "Email: " + i.getEmail();
    }
}
