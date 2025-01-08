package com.example.vaievemclientemobile.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaievemclientemobile.R;
import com.example.vaievemclientemobile.controller.ConexaoController;
import com.example.vaievemclientemobile.databinding.FragmentAlteraSenhaBinding;
import com.example.vaievemclientemobile.databinding.FragmentLoginBinding;
import com.example.vaievemclientemobile.viewModel.InformacoesViewModel;

import modelDominio.Usuario;
import util.Criptografia;


public class AlteraSenhaFragment extends Fragment {
    FragmentAlteraSenhaBinding binding;
    InformacoesViewModel informacoesViewModel;
    boolean resultado;
    Usuario usuarioLogado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate o layout usando o ViewBinding
        binding = FragmentAlteraSenhaBinding.inflate(inflater, container, false);

        // Retorna a raiz da View do binding
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // obtendo a instância do viewModel
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);


        binding.bSalvar.setOnClickListener(view1 -> {
            String senhaantiga = binding.etSenhaAntiga.getText().toString().trim();
            String novasenha = binding.etNovaSenha.getText().toString().trim();

            // Validação básica dos campos
            if (senhaantiga.isEmpty()) {
                binding.etSenhaAntiga.setError("Erro: informe a senha antiga.");
                binding.etSenhaAntiga.requestFocus();
                return;
            }
            if (novasenha.isEmpty()) {
                binding.etNovaSenha.setError("Erro: informe a nova senha.");
                binding.etNovaSenha.requestFocus();
                return;
            }

            // Criptografando a senha
            String senhaAntigaCriptografada = Criptografia.criptografarSenha(senhaantiga);
            String senhaNovaCriptografada = Criptografia.criptografarSenha(novasenha);

            // Mostrando indicador de progresso
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Autenticando...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // Iniciando autenticação em uma thread separada
            new Thread(() -> {
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                Usuario usuarioLogado = informacoesViewModel.getUsuarioLogado();


                // Atualizando a UI na thread principal
                getActivity().runOnUiThread(() -> {
                    progressDialog.dismiss();
                    usuarioLogado.setSenha(senhaAntigaCriptografada);
                    boolean usuarioVerificado = conexaoController.verificaUsuario(usuarioLogado);
                    if (usuarioVerificado) {
                        usuarioLogado.setSenha(senhaNovaCriptografada);
                        boolean alterouSenha = conexaoController.alteraSenha(usuarioLogado);
                        if (alterouSenha) {
                            Toast.makeText(getContext(), "Senha Alterada com Sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Erro ao Alterar Senha.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
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