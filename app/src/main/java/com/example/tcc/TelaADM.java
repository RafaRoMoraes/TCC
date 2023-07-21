package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaADM extends AppCompatActivity {

    private Button udbtn, ativbtn, evenbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adm);

        getSupportActionBar().hide();

        iniciarComponentes();

        ativbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaADM.this, TelaAtividade.class);
                startActivity(intent);
            }
        });

        udbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaADM.this, TelaUD.class);
                startActivity(intent);
            }
        });

        evenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaADM.this, TelaEventos.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarComponentes(){
        udbtn = findViewById(R.id.ud);
        ativbtn = findViewById(R.id.ativbtn);
        evenbtn = findViewById(R.id.eventobt);
    }
}