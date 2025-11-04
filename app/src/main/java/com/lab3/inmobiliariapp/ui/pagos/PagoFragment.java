package com.lab3.inmobiliariapp.ui.pagos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.databinding.FragmentPagoBinding;

public class PagoFragment extends Fragment {

    private FragmentPagoBinding binding;
    private PagoViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPagoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PagoViewModel.class);

        //Le pasamos el Bundle con el idContrato (sin hacer lógica en el fragment)
        vm.recibirArgumentos(getArguments());

        //Observa los pagos y los muestra con el adapter
        vm.getPagos().observe(getViewLifecycleOwner(), pagos -> {
            PagoAdapter adapter = new PagoAdapter(pagos);
            binding.rvPagos.setAdapter(adapter);
            binding.rvPagos.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        //Observa los mensajes (errores, toasts)
        vm.getMensaje().observe(getViewLifecycleOwner(), mensaje ->
                binding.tvMensaje.setText(mensaje)
        );

        return binding.getRoot();
    }
}