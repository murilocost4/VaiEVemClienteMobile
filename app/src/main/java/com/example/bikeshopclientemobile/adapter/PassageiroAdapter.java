package com.example.bikeshopclientemobile.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.bikeshopclientemobile.databinding.ItemListRowSpBinding;

import modelDominio.Passageiro;
import modelDominio.StatusPassageiro;
import com.example.bikeshopclientemobile.controller.ConexaoController;
import com.example.bikeshopclientemobile.viewModel.InformacoesViewModel;

import java.util.List;

public class PassageiroAdapter extends RecyclerView.Adapter<PassageiroAdapter.MyViewHolder> {
    private List<StatusPassageiro> passageiroLista;
    private PassageiroOnClickListener passageiroOnClickListener;
    private ConexaoController conexaoController;
    private InformacoesViewModel informacoesViewModel;

    private ItemListRowSpBinding binding;

    public PassageiroAdapter(List<StatusPassageiro> passageiroLista, PassageiroOnClickListener passageiroOnClickListener, ConexaoController conexaoController, InformacoesViewModel informacoesViewModel) {
        this.passageiroLista = passageiroLista;
        this.passageiroOnClickListener = passageiroOnClickListener;
        this.conexaoController = conexaoController;
        this.informacoesViewModel = informacoesViewModel; // Atribuindo o ViewModel
    }


    @Override
    public PassageiroAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRowSpBinding itemListRowSpBinding = ItemListRowSpBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemListRowSpBinding);
    }

    @Override
    public void onBindViewHolder(final PassageiroAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        StatusPassageiro p = passageiroLista.get(position);
        holder.itemListRowSpBinding.tvSpNome.setText(p.getPassageiro().getNomeUsuario());
        holder.itemListRowSpBinding.tvSpEndereco.setText(p.getPassageiro().getEndereco());
        if (p.getStatus() == 1) {
            holder.itemListRowSpBinding.tvSpStatus.setText("Presente");
            holder.itemListRowSpBinding.tvSpStatus.setTextColor(Color.parseColor("#398D62"));
        } else if (p.getStatus() == 2) {
            holder.itemListRowSpBinding.tvSpStatus.setText("Ausente");
            holder.itemListRowSpBinding.tvSpStatus.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.itemListRowSpBinding.tvSpStatus.setText("Aguardando");
            holder.itemListRowSpBinding.tvSpStatus.setTextColor(Color.parseColor("#4B4B4B"));
        }


        // Configurando clique do botão "Ausente"
        holder.itemListRowSpBinding.bAusente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConexaoController conexaoController = new ConexaoController(informacoesViewModel);

                        boolean resultado = conexaoController.selecionaAusente(p);
                        if (resultado) {
                            ((Activity) view.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.itemListRowSpBinding.tvSpStatus.setText("Ausente");
                                    holder.itemListRowSpBinding.tvSpStatus.setTextColor(Color.parseColor("#FF0000"));
                                    Toast.makeText(view.getContext(), "Status Alterado com Sucesso", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            ((Activity) view.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(view.getContext(), "Erro ao atualizar status", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                thread.start();
            }
        });


        // Configurando clique do botão "Presente"
        holder.itemListRowSpBinding.bPresente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConexaoController conexaoController = new ConexaoController(informacoesViewModel);

                        boolean resultado = conexaoController.selecionaEmbarcou(p);
                        if (resultado) {
                            ((Activity) view.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.itemListRowSpBinding.tvSpStatus.setText("Presente");
                                    holder.itemListRowSpBinding.tvSpStatus.setTextColor(Color.parseColor("#398D62"));
                                    Toast.makeText(view.getContext(), "Status Alterado com Sucesso", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            ((Activity) view.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(view.getContext(), "Erro ao atualizar status", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                thread.start();
            }
        });


    }

    @Override
    public int getItemCount() {
        return passageiroLista.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final ItemListRowSpBinding itemListRowSpBinding;

        public MyViewHolder(ItemListRowSpBinding itemListRowSpBinding) {
            super(itemListRowSpBinding.getRoot());
            this.itemListRowSpBinding = itemListRowSpBinding;
        }
    }

    public interface PassageiroOnClickListener {
        void onClickSp(View view, int position, Passageiro p);

    }
}
