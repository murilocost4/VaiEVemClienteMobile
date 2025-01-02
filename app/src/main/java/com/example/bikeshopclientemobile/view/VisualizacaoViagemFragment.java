package com.example.bikeshopclientemobile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bikeshopclientemobile.R;
import com.example.bikeshopclientemobile.adapter.ViagemAdapter;
import com.example.bikeshopclientemobile.controller.ConexaoController;
import com.example.bikeshopclientemobile.databinding.FragmentVisualizacaoViagemBinding;
import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;

import java.util.ArrayList;

import modelDominio.Viagem;
import modelDominio.Usuario;


public class VisualizacaoViagemFragment extends Fragment {
    FragmentVisualizacaoViagemBinding binding;
    ViagemAdapter viagemAdapter;

    InformacoesViewModel informacoesViewModel;
    ArrayList<Viagem> listaViagens;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_visualizacao_bike, container, false);
        binding = FragmentVisualizacaoViagemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // obtendo a instância do viewModel
        informacoesViewModel = new ViewModelProvider(getActivity()).get(InformacoesViewModel.class);

        // criando a thread para obtenção da lista
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Usuario usLogado = informacoesViewModel.getUsuarioLogado();

                // instanciando e invocando o conexão controller
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                listaViagens = conexaoController.viagemCondutorLista(usLogado.getCodUsuario());
                // verificando o resultado para depois sincronizar as threads
                if (listaViagens != null) {
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
        viagemAdapter = new ViagemAdapter(listaViagens, trataCliqueItem);
        binding.rvVisualizaViagens.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvVisualizaViagens.setItemAnimator(new DefaultItemAnimator());
        binding.rvVisualizaViagens.setAdapter(viagemAdapter);

    }

    ViagemAdapter.ViagemOnClickListener trataCliqueItem = new ViagemAdapter.ViagemOnClickListener() {
        @Override
        public void onClickViagem(View view, int position, Viagem v) {

            Navigation.findNavController(view).navigate(R.id.action_visualizacaoBikeFragment_to_acompanhaViagemFragment);

            // mostrando as informações da bike clicada
            /*Toast.makeText(getContext(), "Origem: " + v.getOrigem() +
                                        ", Destino: " + v.getDestino() +
                                        ", Data: " + v.getData() +
                                        ", Condutor: " + v.getCodCondutor(), Toast.LENGTH_SHORT).show();*/
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}