package com.example.bikeshopclientemobile.controller;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import modelDominio.Usuario;
import modelDominio.Viagem;
import modelDominio.StatusPassageiro;

public class ConexaoController {
    private InformacoesViewModel informacoesViewModel;
    public ConexaoController(InformacoesViewModel informacoesViewModel) {
        this.informacoesViewModel = informacoesViewModel;
    }

    public boolean criaConexaoServidor(String ip, int porta) {
        boolean resultado;
        try {
            // criando a conexão socket com o servidor
            Socket socket = new Socket(ip, porta);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            // salvando as informações do socket no viewModel
            informacoesViewModel.inicializaObjetosSocket(in, out);
            resultado = true;
            Log.e("Teste", "Resultado "+resultado);
        } catch (IOException ioe) {
            Log.e("VaiEVem", "Erro: " + ioe.getMessage());
            resultado = false;
        }
        return resultado;
    }

    public Usuario efetuarLogin(Usuario usuario) {
        Usuario usuarioLogado;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("UsuarioEfetuarLogin");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeObject(usuario);
            usuarioLogado = (Usuario) informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("VaiEVem", "Erro: " + ioe.getMessage());
            usuarioLogado = null;
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            usuarioLogado = null;
        }
        return usuarioLogado;
    }

    public boolean selecionaEmbarcou(StatusPassageiro sp) {
        boolean resultado;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("selecionaEmbarcou");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeObject(sp);
            resultado = (Boolean) informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("VaiEVem","Erro: " + ioe.getMessage());
            resultado = false;
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            resultado = false;
        }

        return resultado;
    }

    public boolean selecionaAusente(StatusPassageiro sp) {
        boolean resultado;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("selecionaAusente");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeObject(sp);
            resultado = (Boolean) informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("VaiEVem","Erro: " + ioe.getMessage());
            resultado = false;
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            resultado = false;
        }

        return resultado;
    }
    public void iniciarViagem(int codViagem, Callback<Boolean> callback) {
        new Thread(() -> {
            int resultado;
            try {
                informacoesViewModel.getOutputStream().writeObject("viagemIniciar");
                informacoesViewModel.getOutputStream().flush();
                Log.d("Teste", "Comando viagemIniciar enviado.");
                informacoesViewModel.getOutputStream().writeInt(codViagem);
                informacoesViewModel.getOutputStream().flush();
                Log.d("Teste", "Codigo da viagem enviado: " + codViagem);
                Log.d("Teste", "Codigo enviado");
                resultado = informacoesViewModel.getInputStream().readInt();
                Log.d("Teste", "Resultado recebido: "+resultado);
            } catch (IOException e) {
                e.printStackTrace();
                resultado = 0;
            }
            boolean finalResultado = (resultado == 1) ? true : false;
            // Update UI on the main thread
            new Handler(Looper.getMainLooper()).post(() -> callback.onResult(finalResultado));
        }).start();
    }


    public boolean finalizarViagem(int codViagem) {

            int resultado;
            try {
                informacoesViewModel.getOutputStream().writeObject("viagemFinalizar");
                //informacoesViewModel.getOutputStream().flush();
                Log.d("Teste", "Comando viagemFinalizar");
                //String mensagem = (String) informacoesViewModel.getInputStream().readObject();
                InputStream inputStream = informacoesViewModel.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                System.out.println(line);
                //System.out.println(mensagem);
                Log.d("Teste", "Mensagem recebida");
                informacoesViewModel.getOutputStream().writeInt(codViagem);
                informacoesViewModel.getOutputStream().flush();
                Log.d("Teste", "Codigo enviado");
                resultado = (int) informacoesViewModel.getInputStream().readInt();
                Log.d("Teste", "Resultado recebido");
            } catch (IOException  e) {
                Log.e("VaiEVem", "Erro: " + e.getMessage());
               e.printStackTrace();
                resultado = 0;
            }
            boolean finalResultado = (resultado == 1) ? true : false;
            return finalResultado;
        };

    // Define a callback interface
    public interface Callback<T> {
        void onResult(T result);
    }

    public ArrayList<Viagem> viagemCondutorLista(int codUsuario) {
        ArrayList<Viagem> listaViagens;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("ViagemCondutorLista");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeInt(codUsuario-1);
            listaViagens = (ArrayList<Viagem>) informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("VaiEVem", "Erro: " + ioe.getMessage());
            listaViagens = null;
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            listaViagens = null;
        }
        return listaViagens;
    }

    public ArrayList<Viagem> viagemLista() {
        ArrayList<Viagem> listaViagens;
        try {
            informacoesViewModel.getOutputStream().writeObject("ViagemLista");
            listaViagens = (ArrayList<Viagem>) informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("VaiEVem", "Erro: " + ioe.getMessage());
            listaViagens = null;
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            listaViagens = null;
        }
        return listaViagens;
    }

    public ArrayList<StatusPassageiro> spLista(Viagem v) {
        ArrayList<StatusPassageiro> listaSp;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("StatusPassageiroLista");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeInt(v.getTrip_id());
            listaSp = (ArrayList<StatusPassageiro>) informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("VaiEVem", "Erro: " + ioe.getMessage());
            listaSp = null;
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            listaSp = null;
        }
        return listaSp;
    }
}







