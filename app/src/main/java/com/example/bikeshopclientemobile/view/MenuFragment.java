package com.example.bikeshopclientemobile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikeshopclientemobile.R;
import com.example.bikeshopclientemobile.databinding.FragmentMenuBinding;
import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;


public class MenuFragment extends Fragment {
    FragmentMenuBinding binding;
    InformacoesViewModel informacoesViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_menu, container, false);
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // obtendo a instância do viewModel
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);
        // definindo a saudação na tela
        binding.tvMenuSaudacao.setText(informacoesViewModel.getUsuarioLogado().getNomeUsuario() + ", seja bem-vindo. Deseja: ");

        // programando o clique nos botões
        binding.bMenuViagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.acao_MenuFragment_VisualizacaoBikeFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}