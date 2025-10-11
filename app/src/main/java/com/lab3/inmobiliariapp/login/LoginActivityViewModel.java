package com.lab3.inmobiliariapp.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lab3.inmobiliariapp.MainActivity;
import com.lab3.inmobiliariapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private Context context;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public LiveData<String> getMensaje() {//Getters para observar el error.
        return mensaje;
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            mensaje.setValue("Debe completar todos los campos.");
            return;
        }

        ApiClient.InmoService apiClient = ApiClient.getApiInmobiliaria();
        Call<String> call = apiClient.loginForm(email, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    //Viene el Token, entonces lo guardo.
                    ApiClient.guardarToken(context, response.body());
                    mensaje.postValue("Bienvenido!");
                    //Navegar al MainActivity
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {
                    mensaje.postValue("Credenciales incorrectas.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mensaje.postValue("Error al contactar con el servidor.");
            }
        });

        //Datos hardcodeados
        //String validEmail = "luisprofessor@gmail.com";
        //String validPassword = "DEEKQW";

        //if (email.equals(validEmail) && password.equals(validPassword)) {
            //isLoggedIn.setValue(true);
        //} else {
            //errorMessage.setValue("Credenciales incorrectas.");
        //}
    }
}
