package com.example.vaievemclientemobile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vaievemclientemobile.R;
import com.example.vaievemclientemobile.adapter.ViagemAdapter;
import com.example.vaievemclientemobile.controller.ConexaoController;
import com.example.vaievemclientemobile.databinding.FragmentVisualizacaoViagemBinding;
import com.example.vaievemclientemobile.viewModel.InformacoesViewModel;

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
        atualizaListagem();

        binding.voltar.setOnClickListener(event -> {
            Navigation.findNavController(view).navigate(R.id.acao_visualizacaoBikeFragment_to_menuFragment);
        });
    }

    public void atualizaListagem() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Usuario usLogado = informacoesViewModel.getUsuarioLogado();

                // instanciando e invocando o conexão controller
                ConexaoController conexaoController = new ConexaoController(informacoesViewModel);
                listaViagens = conexaoController.viagemCondutorLista(usLogado);
                System.out.println("Conjdutor: "+usLogado.getCodUsuario());
                // verificando o resultado para depois sincronizar as threads
                if (listaViagens != null) {
                    // sincronizando as threads para listar as bikes
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // chamando o método responsável por listar as viages
                            if (viagemAdapter == null) {
                                viagemAdapter = new ViagemAdapter(listaViagens, trataCliqueItem);
                                binding.rvVisualizaViagens.setLayoutManager(new LinearLayoutManager(getContext()));
                                binding.rvVisualizaViagens.setItemAnimator(new DefaultItemAnimator());
                                binding.rvVisualizaViagens.setAdapter(viagemAdapter);
                            } else {
                                viagemAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
        thread.start();

        // Ensure the RecyclerView is ready
        binding.rvVisualizaViagens.setVisibility(View.VISIBLE);
    }

    ViagemAdapter.ViagemOnClickListener trataCliqueItem = new ViagemAdapter.ViagemOnClickListener() {
        @Override
        public void onClickViagem(View view, int position, Viagem v) {
            Viagem viagemSelecionada = listaViagens.get(position); // Obtenha a viagem selecionada
            Bundle bundle = new Bundle();
            bundle.putSerializable("viagem", viagemSelecionada);
            Navigation.findNavController(view).navigate(R.id.action_visualizacaoBikeFragment_to_acompanhaViagemFragment, bundle);
        }
    };



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}