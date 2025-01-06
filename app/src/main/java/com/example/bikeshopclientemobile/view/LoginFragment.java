package com.example.bikeshopclientemobile.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikeshopclientemobile.R;
import com.example.bikeshopclientemobile.controller.ConexaoController;
import com.example.bikeshopclientemobile.databinding.FragmentLoginBinding;
import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;

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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // declarando e instanciando o controller do socket
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                resultado = conexaoController.criaConexaoServidor("192.168.2.113", 12345);
                // sincronizando as threads para mostrar o resultado
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // testando o resultado
                        if (resultado == true) {
                            Toast.makeText(getContext(), "Conexão estabelecida com sucesso.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Erro: conexão com o servidor não efetuada.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        thread.start();

        // programando o clique nos botões
        binding.bLoginEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verificando se o usuário informou os dados
                if (!binding.etEmailUsuario.getText().toString().equals("")) {
                    if (!binding.etLoginSenha.getText().toString().equals("")) {
                        // obtendo as informações
                        String usuario = binding.etEmailUsuario.getText().toString();
                        String senha = binding.etLoginSenha.getText().toString();

                        // Criptografando a senha antes de enviar
                        String senhaCriptografada = Criptografia.criptografarSenha(senha);

                        // instanciando o usuario que está se logando
                        usuarioLogado = new Usuario(usuario, senhaCriptografada);


                        // criando a Thread para autenticar o usuário no servidor
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // instanciando o controller e realizando a chamada
                                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                                usuarioLogado = conexaoController.efetuarLogin(usuarioLogado);
                                // sincronizando as threads para tratar o resultado
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // trantando o resultado da autenticação
                                        if (usuarioLogado != null) {
                                            // salvando o usuário logado
                                            informacoesViewModel.inicializaUsuarioLogado(usuarioLogado);
                                            Navigation.findNavController(view).navigate(R.id.acao_LoginFragment_MenuFragment);
                                        } else {
                                            Toast.makeText(getContext(), "Erro: usuário e/ou senha inválidos.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });
                        thread.start();

                    } else {
                        binding.etLoginSenha.setError("Erro: informe a senha.");
                        binding.etLoginSenha.requestFocus();
                    }
                } else {
                    binding.etEmailUsuario.setError("Erro: informe o usuário.");
                    binding.etEmailUsuario.requestFocus();
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}