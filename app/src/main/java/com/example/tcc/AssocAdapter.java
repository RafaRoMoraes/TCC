package com.example.tcc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AssocAdapter extends RecyclerView.Adapter<AssocAdapter.MyViewHolder>{

    Context ctx;
    ArrayList<Associados> associadosArrayList;

    public AssocAdapter(Context ctx, ArrayList<Associados> associadosArrayList) {
        this.ctx = ctx;
        this.associadosArrayList = associadosArrayList;
    }

    @NonNull
    @Override
    public AssocAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ctx).inflate(R.layout.item_lista,parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AssocAdapter.MyViewHolder holder, int position) {

        Associados associados = associadosArrayList.get(position);

        holder.nome.setText(associados.getNome());
        holder.fone.setText(associados.getFone());
        holder.cpf.setText(associados.getCpf());
        holder.email.setText(associados.getEmail());
        holder.end.setText(associados.getEnd());
        holder.nasc.setText(associados.getNasc());
        holder.rg.setText(associados.getRg());

    }

    @Override
    public int getItemCount() {
        return associadosArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome,  fone, rg, email, cpf, nasc, end;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.itemNometv);
            fone = itemView.findViewById(R.id.itemFonetv);
            rg = itemView.findViewById(R.id.itemRgtv);
            email = itemView.findViewById(R.id.itemEmailtv);
            cpf = itemView.findViewById(R.id.itemCpftv);
            nasc = itemView.findViewById(R.id.itemNasctv);
            end = itemView.findViewById(R.id.itemEndtv);
        }
    }
}