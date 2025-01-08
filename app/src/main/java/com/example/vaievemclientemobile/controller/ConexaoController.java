package com.example.vaievemclientemobile.controller;

import android.util.Log;

import com.example.vaievemclientemobile.viewModel.InformacoesViewModel;

import java.io.IOException;
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
            ioe.printStackTrace();
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            usuarioLogado = null;
            classe.printStackTrace();
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
    public boolean iniciarViagem(Viagem v) {
        String mensagem;
        boolean resultado;
        try {
            informacoesViewModel.getOutputStream().writeObject("viagemIniciar");
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


    public boolean finalizarViagem(Viagem v) {
        String mensagem;
        boolean resultado;
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

    // Define a callback interface
    public interface Callback<T> {
        void onResult(T result);
    }

    public ArrayList<Viagem> viagemCondutorLista(Usuario usr) {
        ArrayList<Viagem> listaViagens;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("ViagemCondutorLista");
            Log.d("Teste", "Comando enviado");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            Log.d("Teste", "Mensagem recebida");
            informacoesViewModel.getOutputStream().writeObject(usr);
            Log.d("Teste", "Codigo enviado: "+usr.getCodUsuario());
            listaViagens = (ArrayList<Viagem>) informacoesViewModel.getInputStream().readObject();
            Log.d("Teste", "Lista recebida");
        } catch (IOException ioe) {
            Log.e("VaiEVem", "Erro: " + ioe.getMessage());
            listaViagens = null;
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            listaViagens = null;
        }
        return listaViagens;
    }

    public ArrayList<Viagem> viagemPassageiroLista(Usuario usr) {
        ArrayList<Viagem> listaViagens;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("ViagemPassageiroLista");
            Log.d("Teste", "Comando enviado");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            Log.d("Teste", "Mensagem recebida");
            informacoesViewModel.getOutputStream().writeObject(usr);
            Log.d("Teste", "Codigo enviado: "+usr.getCodUsuario());
            listaViagens = (ArrayList<Viagem>) informacoesViewModel.getInputStream().readObject();
            Log.d("Teste", "Lista recebida");
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

    public Viagem getViagemById(Viagem v) {
        Viagem viagem;
        String mensagem;
        try {
            informacoesViewModel.getOutputStream().writeObject("getViagemById");
            Log.d("Teste","Comando enviado");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            Log.d("Teste","Message received");
            informacoesViewModel.getOutputStream().writeObject(v);
            Log.d("Teste","Viagem enviada");
            viagem = (Viagem) informacoesViewModel.getInputStream().readObject();
            Log.d("Teste","Viagem received");
        } catch (IOException ioe) {
            Log.e("VaiEVem", "Erro: " + ioe.getMessage());
            viagem = null;
            ioe.printStackTrace();
        } catch (ClassNotFoundException classe) {
            Log.e("VaiEVem", "Erro: " + classe.getMessage());
            viagem = null;
            classe.printStackTrace();
        }
        return viagem;
    }

    public boolean alteraSenha(Usuario usr) {
        String mensagem;
        boolean resultado;
        try {
            informacoesViewModel.getOutputStream().writeObject("UsuarioAlterar");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeObject(usr);
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

    public boolean verificaUsuario(Usuario usr) {
        String mensagem;
        boolean resultado;
        try {
            informacoesViewModel.getOutputStream().writeObject("verificaUsuario");
            mensagem = (String) informacoesViewModel.getInputStream().readObject();
            informacoesViewModel.getOutputStream().writeObject(usr);
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
}







