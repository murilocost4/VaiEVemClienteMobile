package com.example.vaievemclientemobile.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaievemclientemobile.databinding.ItemListRowSpBinding;

import modelDominio.Passageiro;
import modelDominio.StatusPassageiro;
import com.example.vaievemclientemobile.controller.ConexaoController;
import com.example.vaievemclientemobile.databinding.ItemListRowSpPassageiroBinding;
import com.example.vaievemclientemobile.viewModel.InformacoesViewModel;

import java.util.List;

public class PassageiroSpAdapter extends RecyclerView.Adapter<PassageiroSpAdapter.MyViewHolder> {
    private List<StatusPassageiro> passageiroLista;
    private PassageiroOnClickListener passageiroOnClickListener;
    private ConexaoController conexaoController;
    private InformacoesViewModel informacoesViewModel;

    private ItemListRowSpPassageiroBinding binding;

    public PassageiroSpAdapter(List<StatusPassageiro> passageiroLista, PassageiroOnClickListener passageiroOnClickListener, ConexaoController conexaoController, InformacoesViewModel informacoesViewModel) {
        this.passageiroLista = passageiroLista;
        this.passageiroOnClickListener = passageiroOnClickListener;
        this.conexaoController = conexaoController;
        this.informacoesViewModel = informacoesViewModel; // Atribuindo o ViewModel
    }


    @Override
    public PassageiroSpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRowSpPassageiroBinding itemListRowSpPassageiroBinding = ItemListRowSpPassageiroBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemListRowSpPassageiroBinding);
    }

    @Override
    public void onBindViewHolder(final PassageiroSpAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        StatusPassageiro p = passageiroLista.get(position);
        holder.itemListRowSpPassageiroBinding.tvSpNome.setText(p.getPassageiro().getNomeUsuario());
        holder.itemListRowSpPassageiroBinding.tvSpEndereco.setText(p.getPassageiro().getEndereco());
        if (p.getStatus() == 1) {
            holder.itemListRowSpPassageiroBinding.tvSpStatus.setText("Presente");
            holder.itemListRowSpPassageiroBinding.tvSpStatus.setTextColor(Color.parseColor("#398D62"));
        } else if (p.getStatus() == 2) {
            holder.itemListRowSpPassageiroBinding.tvSpStatus.setText("Ausente");
            holder.itemListRowSpPassageiroBinding.tvSpStatus.setTextColor(Color.parseColor("#FF0000"));
        } else {
            holder.itemListRowSpPassageiroBinding.tvSpStatus.setText("Aguardando");
            holder.itemListRowSpPassageiroBinding.tvSpStatus.setTextColor(Color.parseColor("#4B4B4B"));
        }





    }

    @Override
    public int getItemCount() {
        return passageiroLista.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final ItemListRowSpPassageiroBinding itemListRowSpPassageiroBinding;

        public MyViewHolder(ItemListRowSpPassageiroBinding itemListRowSpPassageiroBinding) {
            super(itemListRowSpPassageiroBinding.getRoot());
            this.itemListRowSpPassageiroBinding = itemListRowSpPassageiroBinding;
        }
    }

    public interface PassageiroOnClickListener {
        void onClickSp(View view, int position, Passageiro p);

    }
}
