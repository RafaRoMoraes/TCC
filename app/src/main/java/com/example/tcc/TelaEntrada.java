package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class TelaEntrada extends AppCompatActivity {

    private EditText senhatxtE;
    private Button entradabtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_entrada);

        getSupportActionBar().hide();

        iniciarComponentes();

        entradabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senha = senhatxtE.getText().toString();
                if(senha.equals("1234")){
                    Intent intent = new Intent(TelaEntrada.this, TelaADM.class);
                    startActivity(intent);
                }else if(senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,"O campo senha é obrigatório",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    Snackbar snackbar = Snackbar.make(view,"Senha inválida",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void iniciarComponentes(){
        entradabtn = findViewById(R.id.entrarE);
        senhatxtE = findViewById(R.id.senhaE);
    }
}