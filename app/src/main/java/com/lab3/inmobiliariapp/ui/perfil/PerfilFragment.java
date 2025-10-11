package com.lab3.inmobiliariapp.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lab3.inmobiliariapp.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);

        //Primero bbserva el mensaje del ViewModel (para mostrar errores o notificaciones)
        vm.getMensaje().observe(getViewLifecycleOwner(),
                mensaje -> Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show());

        //Despues observa los datos del propietario y los asigna a los edit text
        vm.getPropietario().observe(getViewLifecycleOwner(), p -> {
            binding.etNombre.setText(p.getNombre());
            binding.etApellido.setText(p.getApellido());
            binding.etDni.setText(String.valueOf(p.getDni()));
            binding.etTelefono.setText(p.getTelefono());
            binding.etEmail.setText(p.getEmail());
            binding.etClave.setText(p.getClave());
        });

        //Llama al metodo para cargar los datos
        vm.cargarPropietario();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}