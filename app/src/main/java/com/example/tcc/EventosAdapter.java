package com.example.tcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.MyViewHolder>{

    Context ctx;
    ArrayList<Eventos> eventosArrayList;

    public EventosAdapter(Context ctx, ArrayList<Eventos> eventosArrayList) {
        this.ctx = ctx;
        this.eventosArrayList = eventosArrayList;
    }

    @NonNull
    @Override
    public EventosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ctx).inflate(R.layout.item_lista2,parent, false);

        return new EventosAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosAdapter.MyViewHolder holder, int position) {

        Eventos eventos = eventosArrayList.get(position);

        holder.nomeatv.setText(eventos.getNomeA());
        holder.nomeassoc.setText(eventos.getNome());
        holder.data.setText(eventos.getData());

    }

    @Override
    public int getItemCount() {
        return eventosArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeatv,  nomeassoc, data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeatv = itemView.findViewById(R.id.itemNomeatvTv);
            nomeassoc = itemView.findViewById(R.id.itemNomeassoctv);
            data = itemView.findViewById(R.id.itemdataatvTv);
        }
    }
}