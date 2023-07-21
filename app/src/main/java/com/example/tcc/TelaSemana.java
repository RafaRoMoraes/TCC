package com.example.tcc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelaSemana extends AppCompatActivity {

    Button part;
    TextView nomeAssociado, nomeAtiv, dataA;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<Atividades> atividadesArrayList;
    AtivAdapter ativAdapter;
    String associadoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_semana);

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Procurando os dados...");
        progressDialog.show();

        IniciarComponentes();

        EventChangeListener();

    }
    private void EventChangeListener(){

        db.collection("atividades").orderBy("nomeA", Query.Direction.ASCENDING)
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
                                atividadesArrayList.add(dc.getDocument().toObject(Atividades.class));
                            }

                            ativAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });

    }

    private void IniciarComponentes() {
        part = findViewById(R.id.partbtn);

        nomeAtiv = findViewById(R.id.editText1);
        dataA = findViewById(R.id.editText3);

        recyclerView = findViewById(R.id.recycler2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        atividadesArrayList = new ArrayList<Atividades>();
        ativAdapter = new AtivAdapter(TelaSemana.this, atividadesArrayList, new AtivAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Atividades atividades) {
                Toast.makeText(TelaSemana.this, atividades.getNomeA() + " foi escolhido", Toast.LENGTH_SHORT).show();

                String ativ = atividades.getNomeA();
                String data = atividades.getDataA();

                part.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        associadoId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("associados").document(associadoId);
                        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value != null) {
                                    String nome = value.getString("nome");

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                                    Map<String,Object> eventos = new HashMap<>();
                                    eventos.put("nome",nome);
                                    eventos.put("data", data);
                                    eventos.put("nomeA", ativ);

                                    DocumentReference documentReference = db.collection("eventos").document();
                                    documentReference.set(eventos).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("db", "Sucesso ao salvar os dados");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("db_error", "Erro ao salvar os dados");
                                                }
                                            });
                                }
                            }
                        });
                    }
                });
            }
        });

        recyclerView.setAdapter(ativAdapter);
    }
}