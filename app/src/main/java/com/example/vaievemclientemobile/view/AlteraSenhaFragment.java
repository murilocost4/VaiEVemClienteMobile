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
import android.widget.Toast;

import com.example.vaievemclientemobile.R;
import com.example.vaievemclientemobile.controller.ConexaoController;
import com.example.vaievemclientemobile.databinding.FragmentAlteraSenhaBinding;
import com.example.vaievemclientemobile.databinding.FragmentMenuBinding;
import com.example.vaievemclientemobile.viewModel.InformacoesViewModel;

import modelDominio.Admin;
import modelDominio.Condutor;
import modelDominio.Usuario;
import util.Criptografia;


public class AlteraSenhaFragment extends Fragment {
    FragmentAlteraSenhaBinding binding;
    InformacoesViewModel informacoesViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_menu, container, false);

        binding = FragmentAlteraSenhaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // obtendo a instância do viewModel
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);

        // programando o clique nos botões
        binding.bSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usLogado = informacoesViewModel.getUsuarioLogado();
                Usuario usr = usLogado;
                String email = binding.etEmail.getText().toString().trim();
                String senhaAntiga = binding.etSenhaAntiga.getText().toString().trim();
                String novaSenha = binding.etNovaSenha.getText().toString().trim();
                String senhaAntigaCriptografada = Criptografia.criptografarSenha(senhaAntiga);
                String novaSenhaCriptografada = Criptografia.criptografarSenha(novaSenha);

                usr.setEmail(email);
                usr.setSenha(senhaAntigaCriptografada);


                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                        boolean resultado = conexaoController.verificaUsuario(usr);
                        if (resultado) {
                            usr.setSenha(novaSenhaCriptografada);
                            System.out.println("Senha nova do usuario: "+usr.getSenha());
                            boolean result = conexaoController.alteraSenha(usr.getCodUsuario(), novaSenhaCriptografada);
                            if (result) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Senha alterada com sucesso!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Erro ao alterar senha", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Email e/ou senha antiga incorretos.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                });
                thread.start();
            }
        });

        binding.voltar.setOnClickListener(event -> {
            Navigation.findNavController(view).navigate(R.id.action_alteraSenhaFragment_to_menuFragment);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}