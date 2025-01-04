package com.example.bikeshopclientemobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bikeshopclientemobile.R;
import com.example.bikeshopclientemobile.databinding.FragmentMenuBinding;
import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;

public class Splashscreen extends Fragment {

    FragmentMenuBinding binding;
    InformacoesViewModel informacoesViewModel;
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_splashscreen, container, false);

        // programando o clique nos botões
        binding.bMenuViagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.acao_splashscreen_to_loginFragment);
            }
        });

        // Temporizador da Splash Screen
        new Handler().postDelayed(() -> {
            // Iniciar a próxima atividade (ou fragmento)
            if (getActivity() != null) {
                startActivity(new Intent(getActivity(), Splashscreen.class));
                getActivity().finish();
            }
        }, 3000); // Duração de 3 segundos

        return view;



    }
}
