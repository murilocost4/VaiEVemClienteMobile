package com.example.vaievemclientemobile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaievemclientemobile.R;
import com.example.vaievemclientemobile.databinding.FragmentMenuBinding;
import com.example.vaievemclientemobile.viewModel.InformacoesViewModel;

import modelDominio.Admin;
import modelDominio.Condutor;
import modelDominio.Usuario;


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

        Usuario usLogado = informacoesViewModel.getUsuarioLogado();

        if (usLogado instanceof Admin) {
            binding.bMenuViagens.setText("Painel de Viagens");
        } else {
            binding.bMenuViagens.setText("Minhas Viagens");
        }

        // programando o clique nos botões
        binding.bMenuViagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usLogado = informacoesViewModel.getUsuarioLogado();

                if (usLogado instanceof Admin) {
                    Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_visualizacaoViagemAdminFragment);
                } else if (usLogado instanceof Condutor) {
                    Navigation.findNavController(view).navigate(R.id.acao_MenuFragment_VisualizacaoBikeFragment);
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_visualizacaoViagemPassageiroFragment);
                }


            }
        });

        binding.voltar.setOnClickListener(event -> {
            Navigation.findNavController(view).navigate(R.id.acao_menuFragment_to_loginFragment);
        });

        binding.bAlterarSenha.setOnClickListener(event -> {
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_alteraSenhaFragment);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}