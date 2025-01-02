package com.example.bikeshopclientemobile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bikeshopclientemobile.R;
import com.example.bikeshopclientemobile.adapter.StatusPassageiroAdapter;
import com.example.bikeshopclientemobile.adapter.ViagemAdapter;
import com.example.bikeshopclientemobile.controller.ConexaoController;
import com.example.bikeshopclientemobile.databinding.FragmentAcompanhaViagemBinding;
import com.example.bikeshopclientemobile.databinding.FragmentLoginBinding;
import com.example.bikeshopclientemobile.databinding.FragmentMenuBinding;
import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;

import java.util.ArrayList;

import modelDominio.Passageiro;
import modelDominio.StatusPassageiro;
import modelDominio.Usuario;
import modelDominio.Viagem;

public class AcompanhaViagemFragment extends Fragment {
    FragmentAcompanhaViagemBinding binding;
    InformacoesViewModel informacoesViewModel;
    ConexaoController ccont;
    boolean resultado;
    StatusPassageiro sp;
    Viagem v;
    ArrayList<StatusPassageiro> listaSP;
    StatusPassageiroAdapter spAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_menu, container, false);
        binding = FragmentAcompanhaViagemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // obtendo a instância do viewModel
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);
        binding.tvOrigem.setText(v.getOrigem());
        binding.tvDestino.setText(v.getDestino());

        // programando o clique nos botões
        binding.bIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ccont.viagemIniciar(v);
            }
        });
        binding.bFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ccont.viagemFinalizar(v);
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Usuario usLogado = informacoesViewModel.getUsuarioLogado();

                // instanciando e invocando o conexão controller
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                listaSP = conexaoController.spLista(v);
                // verificando o resultado para depois sincronizar as threads
                if (listaSP != null) {
                    // sincronizando as threads para listar as bikes
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // chamando o método responsável por listar as bikes
                            atualizaListagem();
                        }
                    });
                }
            }
        });
        thread.start();

    }

    public void atualizaListagem() {
        spAdapter = new StatusPassageiroAdapter(listaSP, trataCliqueItem);
        binding.rvVisualizaPassageiros.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvVisualizaPassageiros.setItemAnimator(new DefaultItemAnimator());
        binding.rvVisualizaPassageiros.setAdapter(spAdapter);

    }

    StatusPassageiroAdapter.SpOnClickListener trataCliqueItem = new StatusPassageiroAdapter.SpOnClickListener() {
        @Override
        public void onClickSp(View view, int position, StatusPassageiro sp) {
            // mostrando as informações da bike clicada
            Toast.makeText(getContext(), "Nome: " + sp.getPassageiro().getNomeUsuario() +
                    ", Endereco: " + sp.getPassageiro().getEndereco() +
                    ", Data: " + v.getData(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
