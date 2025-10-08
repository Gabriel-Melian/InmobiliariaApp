package com.lab3.inmobiliariapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.lab3.inmobiliariapp.MainActivity;
import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        setContentView(binding.getRoot());

        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etUsuario.getText().toString();
            String password = binding.etPassword.getText().toString();
            viewModel.login(email, password);
        });

        viewModel.getIsLoggedIn().observe(this, isLoggedIn -> {
            if (isLoggedIn) {
                //Navegar al MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();// Evita volver al login con el botón "Atrás"
            }
        });

        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}