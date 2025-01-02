package com.example.bikeshopclientemobile.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import modelDominio.Usuario;

public class InformacoesViewModel extends ViewModel {
    // informações necessárias para o Socket
    private MutableLiveData<ObjectInputStream> mIn;
    private MutableLiveData<ObjectOutputStream> mOut;

    // usuário logado no sistema
    private MutableLiveData<Usuario> mUsuarioLogado;

    public void inicializaObjetosSocket(ObjectInputStream in, ObjectOutputStream out) {
        // instanciando os mutable live data
        this.mIn = new MutableLiveData<>();
        this.mOut = new MutableLiveData<>();
        // definindo/setando os conteúdos
        this.mIn.postValue(in);
        this.mOut.postValue(out);
    }

    public void inicializaUsuarioLogado(Usuario usuarioLogado) {
        // instanciando o mutable live data
        this.mUsuarioLogado = new MutableLiveData<>();
        // definindo/setando o conteúdo
        this.mUsuarioLogado.postValue(usuarioLogado);
    }

    public ObjectInputStream getInputStream() {
        return mIn.getValue();
    }

    public ObjectOutputStream getOutputStream() {
        return mOut.getValue();
    }

    public Usuario getUsuarioLogado() {
        return mUsuarioLogado.getValue();
    }
}
