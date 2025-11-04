package com.lab3.inmobiliariapp.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.databinding.FragmentDetalleContratoBinding;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        vm.recibirArgumentos(getArguments());

        vm.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            binding.tvDireccion.setText(vm.formatoDireccion());
            binding.tvFechas.setText(vm.formatoFechas());
            binding.tvInquilino.setText(vm.formatoInquilino());
            binding.tvMonto.setText(vm.formatoMonto());
            binding.tvEstado.setText(vm.formatoEstado());
        });

        //Observer para navegacion a pagos
        vm.getNavegarAPagos().observe(getViewLifecycleOwner(), bundle -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_pagos, bundle);
        });

        //Observer para navegar a el fragmentInquilino
        vm.getNavegarAInquilino().observe(getViewLifecycleOwner(), bundle -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_inquilino_detalle, bundle);
        });

        //Estos botones llaman a los metodos del viewModel
        binding.btnVerPagos.setOnClickListener(v -> vm.onVerPagosClicked());
        binding.btnVerInquilino.setOnClickListener(v -> vm.onVerInquilinoClicked());

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}