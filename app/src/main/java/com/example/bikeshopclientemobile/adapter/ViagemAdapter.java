package com.example.bikeshopclientemobile.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikeshopclientemobile.databinding.ItemListRowBinding;

import modelDominio.Viagem;

import java.util.List;

public class ViagemAdapter extends RecyclerView.Adapter<ViagemAdapter.MyViewHolder> {
    private List<Viagem> listaViagens;
    private ViagemOnClickListener viagemOnClickListener;

    public ViagemAdapter(List<Viagem> listaViagens, ViagemOnClickListener viagemOnClickListener) {
        this.listaViagens = listaViagens;
        this.viagemOnClickListener = viagemOnClickListener;
    }

    @Override
    public ViagemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRowBinding itemListRowBinding = ItemListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemListRowBinding);
    }

    @Override
    public void onBindViewHolder(final ViagemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Viagem v = listaViagens.get(position);
        holder.itemListRowBinding.tvViagemDestino.setText(v.getDestino());
        holder.itemListRowBinding.tvViagemData.setText(v.getData());
        if (v.getStatus_viagem() == 0) {
            holder.itemListRowBinding.tvViagemStatus.setText("Não Iniciado");
        } else if (v.getStatus_viagem() == 1) {
            holder.itemListRowBinding.tvViagemStatus.setText("Iniciado");
        } else {
            holder.itemListRowBinding.tvViagemStatus.setText("Finalizado");
        }
        /* CUIDADO: .setText() precisa sempre de String. Se for outro tipo de dado, deve ser feita a conversão com o String.valueOf() */

        // tratando o clique no item
        if (viagemOnClickListener != null) {
            holder.itemListRowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viagemOnClickListener.onClickViagem(holder.itemView, position, v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaViagens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public  ItemListRowBinding itemListRowBinding;
        public MyViewHolder(ItemListRowBinding itemListRowBinding) {
            super(itemListRowBinding.getRoot());
            this.itemListRowBinding = itemListRowBinding;
        }
    }

    public interface ViagemOnClickListener {
        public void onClickViagem(View view, int position, Viagem v);
    }

}



