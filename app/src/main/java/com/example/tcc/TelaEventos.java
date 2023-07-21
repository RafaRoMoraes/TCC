package com.example.tcc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TelaEventos extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Eventos> eventosArrayList;
    EventosAdapter eventosAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_eventos);

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Procurando os dados...");
        progressDialog.show();

        IniciarComponentes();

        EventChangeListener();
    }

    private void IniciarComponentes(){

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        eventosArrayList = new ArrayList<Eventos>();
        eventosAdapter = new EventosAdapter(TelaEventos.this,eventosArrayList);

        recyclerView.setAdapter(eventosAdapter);

    }

    private void EventChangeListener(){

        db.collection("eventos").orderBy("nomeA", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore erro",error.getMessage());
                            return;

                        }

                        for(DocumentChange dc : value.getDocumentChanges()){


                            if(dc.getType() == DocumentChange.Type.ADDED){
                                eventosArrayList.add(dc.getDocument().toObject(Eventos.class));
                            }

                            eventosAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });

    }
}