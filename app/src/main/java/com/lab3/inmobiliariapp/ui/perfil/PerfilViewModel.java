package com.lab3.inmobiliariapp.ui.perfil;

import android.app.Application;
import android.content.Intent;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lab3.inmobiliariapp.login.LoginActivity;
import com.lab3.inmobiliariapp.models.PropietarioModel;
import com.lab3.inmobiliariapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<PropietarioModel> propietario = new MutableLiveData<>();
    private MutableLiveData<PropietarioModel> propietarioActualizado = new MutableLiveData<>();
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private MutableLiveData<Boolean> modoEdicion = new MutableLiveData<>(false);
    private Application app;

    public PerfilViewModel(Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<PropietarioModel> getPropietario() {
        return propietario;
    }

    public LiveData<PropietarioModel> getPropietarioActualizado() { return propietarioActualizado; }

    public LiveData<String> getMensaje() {
        return mensaje;
    }
    public LiveData<Boolean> getModoEdicion() { return modoEdicion; }

    public void cambiarModoEdicion() {
        Boolean actual = modoEdicion.getValue();
        if (actual == null) actual = false;
        modoEdicion.setValue(!actual);
    }

    public void onBotonPresionado(PropietarioModel actual) {
        Boolean enEdicion = modoEdicion.getValue();
        if (enEdicion != null && enEdicion) {
            //Si estaba editando, guarda los cambios
            if (validarCampos(actual)) {
                actualizarPropietario(actual);
            }
        } else {
            //Si estaba en modo lectura, pasa a edición
            cambiarModoEdicion();
        }
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

    public void actualizarPropietario(PropietarioModel propietarioEditado) {
        ApiClient.InmoService apiClient = ApiClient.getApiInmobiliaria();
        String token = ApiClient.leerToken(app);

        if (token == null || token.isEmpty()) {
            mensaje.postValue("Token no encontrado. Inicie sesión nuevamente.");
            return;
        }

        //Obtengo el ID actual del propietario cargado
        PropietarioModel propietarioActual = propietario.getValue();
        if (propietarioActual != null) {
            propietarioEditado.setIdPropietario(propietarioActual.getIdPropietario());//Le seteo el mismo ID
        }

        //La clave debe enviarse nula
        propietarioEditado.setClave(null);

        //Guardo email anterior antes de actualizar
        String emailAnterior = propietarioActual != null ? propietarioActual.getEmail() : null;

        Call<PropietarioModel> call = apiClient.actualizarProp("Bearer " + token, propietarioEditado);
        //La API espera "Bearer " + token

        call.enqueue(new Callback<PropietarioModel>() {
            @Override
            public void onResponse(Call<PropietarioModel> call, Response<PropietarioModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    propietario.postValue(response.body());
                    //mensaje.postValue("Propietario actualizado correctamente.");
                    modoEdicion.postValue(false);//Volver a modo lectura

                    //Si modifico el email, se cierra la sesion y redirige al login (Para el nuevo Token)
                    if (emailAnterior != null && !emailAnterior.equals(response.body().getEmail())) {
                        ApiClient.guardarToken(app, null);
                        mensaje.postValue("Email modificado. Inicia sesion nuevamente.");

                        Intent intent = new Intent(app, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        app.startActivity(intent);
                    } else {
                        mensaje.postValue("Datos actualizados correctamente.");
                    }

                } else {
                    mensaje.postValue("Error al actualizar el propietario.");
                }
            }

            @Override
            public void onFailure(Call<PropietarioModel> call, Throwable t) {
                mensaje.postValue("Error al contactar con el servidor.");
            }
        });
    }

    private boolean validarCampos(PropietarioModel p) {
        if (p.getNombre().isEmpty() || p.getApellido().isEmpty() ||
                p.getDni().isEmpty() || p.getTelefono().isEmpty() || p.getEmail().isEmpty()) {
            mensaje.postValue("Todos los campos son obligatorios.");
            return false;
        }

        //Validar numeros en DNI
        try {
            Integer.parseInt(p.getDni());
        } catch (NumberFormatException e) {
            mensaje.postValue("El DNI debe ser un número válido.");
            return false;
        }

        //Formato Email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(p.getEmail()).matches()) {
            mensaje.postValue("Ingrese un correo electrónico válido.");
            return false;
        }

        return true;
    }
}