package com.example.bikeshopclientemobile.view;

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

import com.example.bikeshopclientemobile.R;
import com.example.bikeshopclientemobile.controller.ConexaoController;
import com.example.bikeshopclientemobile.databinding.FragmentLoginBinding;
import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;

import modelDominio.Admin;
import modelDominio.Usuario;
import util.Criptografia;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    InformacoesViewModel informacoesViewModel;
    boolean resultado;
    Usuario usuarioLogado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate o layout usando o ViewBinding
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // Configurar o clique no TextView
        TextView forgotPassword = binding.tvForgotPassword; // Use o binding para acessar a View
        forgotPassword.setOnClickListener(v -> {
            // Abrir o WhatsApp com o número desejado
            String url = "https://wa.me/5551999281148?text=Oii%2C%20esqueci%20minha%20senha.%20Voc%C3%AA%20pode%20me%20ajudar%3F%20";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        // Retorna a raiz da View do binding
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // obtendo a instância do viewModel
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);



        // criando a thread para a criação da conexão socket com o servidor
        Thread thread = new Thread(() -> {
            // Declarando e instanciando o controller do socket
            ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
            boolean resultado;

            // Tentando estabelecer a conexão
            try {
                resultado = conexaoController.criaConexaoServidor("192.168.210.124", 12345);
            } catch (Exception e) {
                e.printStackTrace();
                resultado = false;
            }

            // Sincronizando as threads para mostrar o resultado
            boolean finalResultado = resultado;
            getActivity().runOnUiThread(() -> {
                // Exibindo o resultado da conexão
                if (finalResultado) {
                    Toast.makeText(getContext(), "Conexão estabelecida com sucesso.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Erro: conexão com o servidor não efetuada.", Toast.LENGTH_SHORT).show();
                }
            });
        });

// Iniciar a thread
        thread.start();


        binding.bLoginEntrar.setOnClickListener(view1 -> {
            String email = binding.etEmailUsuario.getText().toString().trim();
            String senha = binding.etLoginSenha.getText().toString().trim();

            // Validação básica dos campos
            if (email.isEmpty()) {
                binding.etEmailUsuario.setError("Erro: informe o usuário.");
                binding.etEmailUsuario.requestFocus();
                return;
            }
            if (senha.isEmpty()) {
                binding.etLoginSenha.setError("Erro: informe a senha.");
                binding.etLoginSenha.requestFocus();
                return;
            }

            // Criptografando a senha
            String senhaCriptografada = Criptografia.criptografarSenha(senha);

            // Mostrando indicador de progresso
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Autenticando...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // Iniciando autenticação em uma thread separada
            new Thread(() -> {
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                Usuario usuarioLogado = conexaoController.efetuarLogin(new Usuario(email, senha));

                // Atualizando a UI na thread principal
                getActivity().runOnUiThread(() -> {
                    progressDialog.dismiss();
                    if (usuarioLogado != null) {
                        informacoesViewModel.inicializaUsuarioLogado(usuarioLogado);

                            Navigation.findNavController(view).navigate(R.id.acao_LoginFragment_MenuFragment);


                    } else {
                        Toast.makeText(getContext(), "Erro: usuário e/ou senha inválidos.", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}