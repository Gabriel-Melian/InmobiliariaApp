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
import com.lab3.inmobiliariapp.models.Contrato;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;
    private Contrato contrato;

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
            //Mostrar los titulos fijos y los datos formateados
            binding.tvDireccion.setText("Dirección: " + vm.obtenerDireccion());
            binding.tvFechas.setText(vm.obtenerFechas());
            binding.tvInquilino.setText("Inquilino: " + vm.obtenerInquilino());
            binding.tvMonto.setText("Monto: " + vm.obtenerMonto());
            binding.tvEstado.setText("Estado: " + vm.obtenerEstado());
            this.contrato = contrato;//Inicializo contrato
        });

        //Esto lo sacooooo!!!!!
        //Observer para navegacion a pagos
//        vm.getNavegarAPagos().observe(getViewLifecycleOwner(), bundle -> {
//            Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_pagos, bundle);
//        });
//
//        //Observer para navegar a el fragmentInquilino
//        vm.getNavegarAInquilino().observe(getViewLifecycleOwner(), bundle -> {
//            Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_inquilino_detalle, bundle);
//        });

        //Estos botones llaman a los metodos del viewModel
        binding.btnVerPagos.setOnClickListener(v -> {
            //Navego al fragment de pagos
            Bundle bundle = new Bundle();
            bundle.putInt("idContrato", contrato.getIdContrato());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_pagos, bundle);
        });
        binding.btnVerInquilino.setOnClickListener(v -> {
            //if (contrato == null || contrato.getInquilino() == null) return;
            //Navego al fragment de inquilino
            Bundle bundle = new Bundle();
            bundle.putSerializable("inquilino", contrato.getInquilino());
            Navigation.findNavController(v).navigate(R.id.nav_inquilino_detalle, bundle);
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        // TODO: Use the ViewModel
    }

}