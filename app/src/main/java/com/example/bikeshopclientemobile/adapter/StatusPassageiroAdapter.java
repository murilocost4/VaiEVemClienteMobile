package com.example.bikeshopclientemobile.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikeshopclientemobile.databinding.ItemListRowBinding;

import modelDominio.StatusPassageiro;
import modelDominio.Viagem;

import java.util.List;

public class StatusPassageiroAdapter extends RecyclerView.Adapter<StatusPassageiroAdapter.MyViewHolder> {
    private List<StatusPassageiro> listaSP;
    private SpOnClickListener spOnClickListener;

    public StatusPassageiroAdapter(List<StatusPassageiro> listaSP, SpOnClickListener viagemOnClickListener) {
        this.listaSP = listaSP;
        this.spOnClickListener = spOnClickListener;
    }

    @Override
    public StatusPassageiroAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRowBinding itemListRowBinding = ItemListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemListRowBinding);
    }

    @Override
    public void onBindViewHolder(final StatusPassageiroAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        StatusPassageiro sp = listaSP.get(position);
        holder.itemListRowBinding.tvSpNome.setText(sp.getPassageiro().getNomeUsuario());
        holder.itemListRowBinding.tvSpEndereco.setText(sp.getPassageiro().getEndereco());
        /* CUIDADO: .setText() precisa sempre de String. Se for outro tipo de dado, deve ser feita a conversão com o String.valueOf() */

        // tratando o clique no item
        if (spOnClickListener != null) {
            holder.itemListRowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spOnClickListener.onClickSp(holder.itemView, position, sp);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaSP.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public  ItemListRowBinding itemListRowBinding;
        public MyViewHolder(ItemListRowBinding itemListRowBinding) {
            super(itemListRowBinding.getRoot());
            this.itemListRowBinding = itemListRowBinding;
        }
    }

    public interface SpOnClickListener {
        public void onClickSp(View view, int position, StatusPassageiro sp);
    }

}



