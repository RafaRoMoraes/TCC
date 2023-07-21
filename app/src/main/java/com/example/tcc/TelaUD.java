package com.example.tcc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TelaUD extends AppCompatActivity {

    Button deletebt;
    RecyclerView recyclerView;
    ArrayList<Associados> associadosArrayList;
    AssocAdapter assocAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    EditText nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_ud);

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Procurando os dados...");
        progressDialog.show();

        IniciarComponentes();

        EventChangeListener();

        deletebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String nomeE = nome.getText().toString();
               nome.setText("");
               DeleteData(nomeE);
            }
        });
    }

    private void DeleteData(String nome){
        db.collection("associados").whereEqualTo("nome", nome).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){

                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();
                    db.collection("associados").document(documentID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(TelaUD.this, "Dado apagado corretamente", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(TelaUD.this, "Ocorreu algum erro", Toast.LENGTH_SHORT).show();

                        }
                    });

                }else{

                    Toast.makeText(TelaUD.this, "Falhou", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void IniciarComponentes(){
        deletebt = findViewById(R.id.apagarbt);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nome = findViewById(R.id.excluirNo);
        db = FirebaseFirestore.getInstance();
        associadosArrayList = new ArrayList<Associados>();
        assocAdapter = new AssocAdapter(TelaUD.this,associadosArrayList);

        recyclerView.setAdapter(assocAdapter);

    }

    private void EventChangeListener(){

        db.collection("associados").orderBy("nome", Query.Direction.ASCENDING)
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
                        associadosArrayList.add(dc.getDocument().toObject(Associados.class));
                    }

                    assocAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                }

            }
        });

    }
}