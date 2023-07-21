package com.example.tcc;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AtivAdapter extends RecyclerView.Adapter<AtivAdapter.MyViewHolder> {

    Context ctx;
    ArrayList<Atividades> atividadesArrayList;
    ItemClickListener mItemListener;

    public AtivAdapter(Context ctx, ArrayList<Atividades> atividadesArrayList, ItemClickListener itemClickListener) {
        this.ctx = ctx;
        this.atividadesArrayList = atividadesArrayList;
        this.mItemListener = itemClickListener;
    }

    @NonNull
    @Override
    public AtivAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ctx).inflate(R.layout.item_lista3, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AtivAdapter.MyViewHolder holder, int position) {

        Atividades atividades = atividadesArrayList.get(position);

        holder.nomeatv.setText(atividades.getNomeA());
        holder.nomeprof.setText(atividades.getProfA());
        holder.data.setText(atividades.getDataA());
        holder.desc.setText(atividades.getDescA());
        holder.dia.setText(atividades.getDias());

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(atividades);
        });

    }

    @Override
    public int getItemCount() {
        return atividadesArrayList.size();
    }

    public interface ItemClickListener{
        void onItemClick(Atividades atividades);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeatv, nomeprof, data, desc, dia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.itemdesctv);
            dia = itemView.findViewById(R.id.itemdiastv);
            nomeatv = itemView.findViewById(R.id.itemNomeatv);
            nomeprof = itemView.findViewById(R.id.itemproftv);
            data = itemView.findViewById(R.id.itemdatatv);
        }
    }
}