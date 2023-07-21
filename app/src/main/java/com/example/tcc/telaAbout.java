package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class telaAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_about);

        getSupportActionBar().hide();
    }
}