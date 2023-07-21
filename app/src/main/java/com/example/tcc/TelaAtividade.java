package com.example.tcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaAtividade extends AppCompatActivity {
    Button adcbt;
    EditText nomeA, dataA, descA, profA;
    Spinner dias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_atividade);

        getSupportActionBar().hide();

        IniciarComponentes();

        dias.setPrompt("Selecione o dia:");

        adcbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeA.getText().toString();
                String data = dataA.getText().toString();
                String desc = descA.getText().toString();
                String prof = profA.getText().toString();
                if(nome.isEmpty() || data.isEmpty() || desc.isEmpty() || prof.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,"Preencha todos os campos",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                SalvarDados();
                }
            }
        });
    }

    private void SalvarDados(){
        String nome = nomeA.getText().toString();
        String data = dataA.getText().toString();
        String desc = descA.getText().toString();
        String prof = profA.getText().toString();
        String dias2 = dias.getSelectedItem().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> atividades = new HashMap<>();
        atividades.put("nomeA",nome);
        atividades.put("dataA",data);
        atividades.put("descA",desc);
        atividades.put("profA",prof);
        atividades.put("dias",dias2);

        DocumentReference documentReference = db.collection("atividades").document();
        documentReference.set(atividades).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void IniciarComponentes(){
        nomeA = findViewById(R.id.nomeAtivtxt);
        dataA = findViewById(R.id.datatxt);
        descA = findViewById(R.id.desctxt);
        profA = findViewById(R.id.proftxt);

        adcbt = findViewById(R.id.adcbtn);

        dias = (Spinner) findViewById(R.id.diaS);
    }
}