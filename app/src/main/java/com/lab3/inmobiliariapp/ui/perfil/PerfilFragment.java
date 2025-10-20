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
import androidx.navigation.fragment.NavHostFragment;

import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.databinding.FragmentPerfilBinding;
import com.lab3.inmobiliariapp.models.PropietarioModel;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private ClaveFragmentViewModel vmClave;
    private PerfilViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);

        //Primero observa el mensaje del ViewModel (para mostrar errores o notificaciones)
        vm.getMensaje().observe(getViewLifecycleOwner(),
                mensaje -> Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show());

        //Despues observa los datos del propietario y los asigna a los edit text
        vm.getPropietario().observe(getViewLifecycleOwner(), p -> {
            binding.etNombre.setText(p.getNombre());
            binding.etApellido.setText(p.getApellido());
            binding.etDni.setText(String.valueOf(p.getDni()));
            binding.etTelefono.setText(p.getTelefono());
            binding.etEmail.setText(p.getEmail());
        });

        // Observa cambios en modo edición
        vm.getModoEdicion().observe(getViewLifecycleOwner(), enEdicion -> {
            boolean editable = enEdicion != null && enEdicion;
            binding.etNombre.setEnabled(editable);
            binding.etApellido.setEnabled(editable);
            binding.etDni.setEnabled(editable);
            binding.etTelefono.setEnabled(editable);
            binding.etEmail.setEnabled(editable);
            binding.btnActualizar.setText(editable ? "Guardar" : "Editar");
        });

        //Btn Clave
        binding.btnClave.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.nav_cambiar_clave);
        });

        //Btn principal de Editar/Guardar
        binding.btnActualizar.setOnClickListener(v -> {
            PropietarioModel propietario = new PropietarioModel(
                    vm.getPropietario().getValue().getIdPropietario(),
                    binding.etNombre.getText().toString(),
                    binding.etApellido.getText().toString(),
                    binding.etDni.getText().toString(),
                    binding.etTelefono.getText().toString(),
                    binding.etEmail.getText().toString()
            );
            vm.onBotonPresionado(propietario);
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