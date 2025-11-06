package com.lab3.inmobiliariapp.login;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.lab3.inmobiliariapp.MainActivity;
import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LlamarViewModel llamarVM;
    private ActivityLoginBinding binding;
    private LoginActivityViewModel viewModel;
    private static final int REQ_CALL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Inicializar ViewModels
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        llamarVM = new ViewModelProvider(this).get(LlamarViewModel.class);

        pedirPermisoCall();

        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etUsuario.getText().toString();
            String password = binding.etPassword.getText().toString();
            viewModel.login(email, password);
        });

        // Observador: Solo en LoginActivity se ejecutara el Intent de llamada
        llamarVM.getDeteccionAgitado().observe(this, agitado -> {
            if (Boolean.TRUE.equals(agitado)) {
                //bloqueo extra para no spamear toasts:
                Toast.makeText(this, "Teléfono agitado. Intentando llamar...", Toast.LENGTH_SHORT).show();
                hacerLlamadaSiPermiso();
            }
        });

        viewModel.getMensaje().observe(this,
                mensaje -> Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show());

    }

    private void pedirPermisoCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQ_CALL);
        }
    }

    private void hacerLlamadaSiPermiso() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            String telefono = "tel:2664680489";//Llama a mi numero
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(telefono));
            startActivity(intent);
        } else {
            //pedir permiso y avisar al usuario
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQ_CALL);
            Toast.makeText(this, "Otorga permiso para realizar llamadas.", Toast.LENGTH_SHORT).show();
        }
    }

    //Manejo resultado de la peticion de permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso CALL_PHONE concedido.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso CALL_PHONE denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}