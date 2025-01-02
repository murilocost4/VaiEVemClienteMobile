package com.example.bikeshopclientemobile.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikeshopclientemobile.databinding.ItemListRowSpBinding;

import modelDominio.StatusPassageiro;
import com.example.bikeshopclientemobile.controller.ConexaoController;

import java.util.List;

public class StatusPassageiroAdapter extends RecyclerView.Adapter<StatusPassageiroAdapter.MyViewHolder> {
    private List<StatusPassageiro> listaSP;
    private SpOnClickListener spOnClickListener;
    private ConexaoController conexaoController;

    public StatusPassageiroAdapter(List<StatusPassageiro> listaSP, SpOnClickListener spOnClickListener, ConexaoController conexaoController) {
        this.listaSP = listaSP;
        this.spOnClickListener = spOnClickListener;
        this.conexaoController = conexaoController;
    }

    @Override
    public StatusPassageiroAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRowSpBinding itemListRowSpBinding = ItemListRowSpBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemListRowSpBinding);
    }

    @Override
    public void onBindViewHolder(final StatusPassageiroAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        StatusPassageiro sp = listaSP.get(position);
        holder.itemListRowSpBinding.tvSpNome.setText(sp.getPassageiro().getNomeUsuario());
        holder.itemListRowSpBinding.tvSpEndereco.setText(sp.getPassageiro().getEndereco());

        // Configurando clique do botão "Presente"
        holder.itemListRowSpBinding.bPresente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexaoController.selecionaEmbarcou(sp);
                if (spOnClickListener != null) {
                    spOnClickListener.onClickSp(v, position, sp);
                }
            }
        });

        // Configurando clique do botão "Ausente"
        holder.itemListRowSpBinding.bAusente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexaoController.selecionaAusente(sp);
                if (spOnClickListener != null) {
                    spOnClickListener.onClickSp(v, position, sp);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSP.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final ItemListRowSpBinding itemListRowSpBinding;

        public MyViewHolder(ItemListRowSpBinding itemListRowSpBinding) {
            super(itemListRowSpBinding.getRoot());
            this.itemListRowSpBinding = itemListRowSpBinding;
        }
    }

    public interface SpOnClickListener {
        void onClickSp(View view, int position, StatusPassageiro sp);
    }
}
