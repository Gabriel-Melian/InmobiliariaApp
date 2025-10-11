package com.lab3.inmobiliariapp.ui.inmuebles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InmuebleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InmuebleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Inmueble fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}