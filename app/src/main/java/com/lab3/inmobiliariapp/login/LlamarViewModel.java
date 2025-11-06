package com.lab3.inmobiliariapp.login;

import android.app.Application;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class LlamarViewModel extends AndroidViewModel implements SensorEventListener {

    private Sensor acelerometro;
    private long ultimoTiempo;
    private long ultimaDeteccion = 0;
    private float ultimo_x, ultimo_y, ultimo_z;
    private static final long COOLDOWN_MS = 3000;// 3 segundos
    private static final int AGITAR = 600;
    private MutableLiveData<Boolean> deteccionAgitado = new MutableLiveData<>(false);
    public LlamarViewModel(@NonNull Application application) {
        super(application);

        SensorManager sensorManager = (SensorManager) application.getSystemService(Application.SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public MutableLiveData<Boolean> getDeteccionAgitado() {
        return deteccionAgitado;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        long tiempoActual = System.currentTimeMillis();

        if ((tiempoActual - ultimoTiempo) <= 100) return;
        long tiempoDiferencia = (tiempoActual - ultimoTiempo);
        ultimoTiempo = tiempoActual;

        float velocidad = Math.abs(x + y + z - ultimo_x - ultimo_y - ultimo_z) / tiempoDiferencia * 10000;

        if (velocidad > AGITAR && (tiempoActual - ultimaDeteccion > COOLDOWN_MS)) {
            ultimaDeteccion = tiempoActual;
            deteccionAgitado.postValue(true);
        } else {
            //No postear false constantemente, pero si se quiere, se puede
            //deteccionAgitado.postValue(false);
        }

        ultimo_x = x;
        ultimo_y = y;
        ultimo_z = z;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        SensorManager sensorManager = (SensorManager) getApplication().getSystemService(Application.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

}
