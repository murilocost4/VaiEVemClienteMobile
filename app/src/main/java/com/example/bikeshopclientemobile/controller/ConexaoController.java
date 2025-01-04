package com.example.bikeshopclientemobile.controller;

import android.util.Log;

import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import modelDominio.Usuario;
import modelDominio.Admin;
import modelDominio.Condutor;
import modelDominio.Passageiro;
import modelDominio.Viagem;
import modelDominio.StatusPassageiro;

import modelDominio.Usuario;

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
        } catch (IOException ioe) {
            Log.e("BikeShop", "Erro: " + ioe.getMessage());
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

    public boolean viagemIniciar(Viagem v) {
        boolean resultado;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("viagemIniciar");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeObject(v);
            resultado = (Boolean) informacoesViewModel.getInputStream().readObject();
        } catch (IOException ioe) {
            Log.e("VaiEVem","Erro de io: " + ioe.getMessage());
            resultado = false;
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro ao encontrar classe: " + classe.getMessage());
            resultado = false;
        }

        return resultado;
    }

    public boolean viagemFinalizar(Viagem v) {
        boolean resultado;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("viagemFinalizar");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeObject(v);
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







