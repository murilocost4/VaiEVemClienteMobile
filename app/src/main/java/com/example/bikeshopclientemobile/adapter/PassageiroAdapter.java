package com.example.bikeshopclientemobile.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bikeshopclientemobile.databinding.ItemListRowSpBinding;

import modelDominio.Passageiro;
import modelDominio.StatusPassageiro;
import com.example.bikeshopclientemobile.controller.ConexaoController;

import java.util.List;

public class PassageiroAdapter extends RecyclerView.Adapter<PassageiroAdapter.MyViewHolder> {
    private List<StatusPassageiro> passageiroLista;
    private PassageiroOnClickListener passageiroOnClickListener;
    private ConexaoController conexaoController;

    public PassageiroAdapter(List<StatusPassageiro> passageiroLista, PassageiroOnClickListener passageiroOnClickListener, ConexaoController conexaoController) {
        this.passageiroLista = passageiroLista;
        this.passageiroOnClickListener = passageiroOnClickListener;
        this.conexaoController = conexaoController;
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

        // Configurando clique do botão "Presente"

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
