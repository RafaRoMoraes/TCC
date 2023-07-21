package com.example.tcc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPrincipal extends AppCompatActivity {

    private TextView nomeAssociado, emailAssociado, foneAssociado;
    private Button desconectarBtn, admbt, sembt, sobrebt, editarbt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String associadoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal2);

        iniciarComponentes();
        getSupportActionBar().hide();

        admbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, TelaEntrada.class);
                startActivity(intent);
            }
        });

        sembt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, TelaSemana.class);
                startActivity(intent);
            }
        });

        desconectarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });

        sobrebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, telaAbout.class);
                startActivity(intent);
            }
        });

        editarbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, FormEdit.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        associadoId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("associados").document(associadoId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    nomeAssociado.setText(documentSnapshot.getString("nome"));
                    foneAssociado.setText(documentSnapshot.getString("fone"));
                    emailAssociado.setText(email);
                }

            }
        });
    }

    private void iniciarComponentes(){
        nomeAssociado = findViewById(R.id.usuariotxt);
        emailAssociado = findViewById(R.id.txtVEmail);
        foneAssociado = findViewById(R.id.fonetxtV);

        admbt = findViewById(R.id.admbtn);
        sembt = findViewById(R.id.semanabtn);
        desconectarBtn = findViewById(R.id.desconectBtn);
        sobrebt = findViewById(R.id.aboutbt);
        editarbt = findViewById(R.id.editbtn);
    }
}