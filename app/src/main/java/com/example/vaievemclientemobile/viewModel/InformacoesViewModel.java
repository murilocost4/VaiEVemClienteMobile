package com.example.vaievemclientemobile.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import modelDominio.Usuario;
import util.Criptografia;  // Importando a classe Criptografia

public class InformacoesViewModel extends ViewModel {
    // Informações necessárias para o Socket
    private MutableLiveData<ObjectInputStream> mIn = new MutableLiveData<>();
    private MutableLiveData<ObjectOutputStream> mOut = new MutableLiveData<>();

    // Usuário logado no sistema
    private MutableLiveData<Usuario> mUsuarioLogado = new MutableLiveData<>();

    // Inicializa os objetos do socket
    public void inicializaObjetosSocket(ObjectInputStream in, ObjectOutputStream out) {
        Log.d("InformacoesViewModel", "Chamando inicializaObjetosSocket.");

        if (in != null && out != null) {
            Log.d("InformacoesViewModel", "Inicializando InputStream e OutputStream.");
            mIn.postValue(in);
            mOut.postValue(out);
        } else {
            Log.e("InformacoesViewModel", "InputStream ou OutputStream é nulo.");
        }
    }

    // Inicializa o usuário logado no sistema
    public void inicializaUsuarioLogado(Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            Log.e("InformacoesViewModel", "UsuarioLogado é nulo.");
            return;
        }

        // Verificar se a senha está nula e criptografá-la
        if (usuarioLogado.getSenha() != null) {
            String senhaCriptografada = Criptografia.criptografarSenha(usuarioLogado.getSenha());
            usuarioLogado.setSenha(senhaCriptografada);  // Atualiza a senha com a versão criptografada
        }

        // Atualiza o usuário logado
        mUsuarioLogado.postValue(usuarioLogado);
    }

    // Acessa o InputStream
    public ObjectInputStream getInputStream() {
        Log.d("InformacoesViewModel", "Chamando getInputStream.");
        if (mIn.getValue() == null) {
            Log.e("InformacoesViewModel", "InputStream não foi inicializado.");
            return null;
        }
        return mIn.getValue();
    }

    // Acessa o OutputStream
    public ObjectOutputStream getOutputStream() {
        Log.d("InformacoesViewModel", "Chamando getOutputStream.");
        if (mOut.getValue() == null) {
            Log.e("InformacoesViewModel", "OutputStream não foi inicializado.");
            return null;
        }
        return mOut.getValue();
    }

    // Acessa o usuário logado
    public Usuario getUsuarioLogado() {
        if (mUsuarioLogado.getValue() == null) {
            Log.e("InformacoesViewModel", "UsuarioLogado não foi inicializado.");
            return null;
        }
        return mUsuarioLogado.getValue();
    }
}
