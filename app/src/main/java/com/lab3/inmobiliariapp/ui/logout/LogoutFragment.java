package com.lab3.inmobiliariapp.ui.logout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lab3.inmobiliariapp.R;
import com.lab3.inmobiliariapp.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;
    private LogoutViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        //Observer para mensajes del ViewModel
        viewModel.getMensaje().observe(getViewLifecycleOwner(),
                mensaje -> Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show());

        //Cuando el usuario toca "Cerrar sesión"
        binding.btnCerrar.setOnClickListener(v -> viewModel.logout());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}