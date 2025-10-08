package com.lab3.inmobiliariapp.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginActivityViewModel extends ViewModel {

    private MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoggedIn() {//Getters para observar si esta o no logueado.
        return isLoggedIn;
    }

    public LiveData<String> getErrorMessage() {//Getters para observar el error.
        return errorMessage;
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.setValue("Debe completar todos los campos.");
            return;
        }

        //Profe hardcodeado
        String validEmail = "luisprofessor@gmail.com";
        String validPassword = "DEEKQW";

        if (email.equals(validEmail) && password.equals(validPassword)) {
            isLoggedIn.setValue(true);
        } else {
            errorMessage.setValue("Credenciales incorrectas.");
        }
    }
}
