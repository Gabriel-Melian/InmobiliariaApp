package com.lab3.inmobiliariapp.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.databinding.FragmentClaveBinding;

public class ClaveFragment extends Fragment {
    private ClaveFragmentViewModel vm;
    private FragmentClaveBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ClaveFragmentViewModel.class);
        binding = FragmentClaveBinding.inflate(inflater, container, false);

        //Observer para mostrar mensajes
        vm.getMensaje().observe(getViewLifecycleOwner(),
                mensaje -> Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show());

        //Btn Guardar
        binding.btnGuardar.setOnClickListener(v -> {
            String claveActual = binding.etClaveActual.getText().toString();
            String claveNueva = binding.etClaveNueva.getText().toString();
            vm.cambiarClave(claveActual, claveNueva);
        });

        return binding.getRoot();
    }

}