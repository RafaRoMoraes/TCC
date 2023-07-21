package com.example.tcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormCadastro extends AppCompatActivity {
    private EditText nometxt, fonetxt, emailtxt, senhatxt, cpftxt, rgtxt, endtxt, nasctxt;
    private Button salvarbtn;
    String[] mensagens = {"Preencha todos os campos", "Cadastro realizado com sucesso"};
    String associadoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);
        getSupportActionBar().hide();
        IniciarComponentes();

        salvarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = nometxt.getText().toString();
                String email = emailtxt.getText().toString();
                String senha = senhatxt.getText().toString();
                String fone = fonetxt.getText().toString();
                String rg = rgtxt.getText().toString();
                String cpf = cpftxt.getText().toString();
                String nasc = nasctxt.getText().toString();
                String end = endtxt.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || fone.isEmpty() || rg.isEmpty() || cpf.isEmpty() || nasc.isEmpty() || end.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    cadastrarUsuario(view);
                }

            }
        });
    }

    private void cadastrarUsuario(View view){

        String email = emailtxt.getText().toString();
        String senha = senhatxt.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(view,mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    String erro;
                    try{
                        throw task.getException();

                    }catch(FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha com no mínimo 6 e no máximo 16 caracteres";
                    }catch(FirebaseAuthUserCollisionException e){
                        erro = "Este endereço de e-mail já está sendo utilizado";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail inválido";
                    }catch(Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }

                    Snackbar snackbar = Snackbar.make(view,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });

    }

    private void SalvarDadosUsuario(){
        String nome = nometxt.getText().toString();
        String fone = fonetxt.getText().toString();
        String rg = rgtxt.getText().toString();
        String senha = senhatxt.getText().toString();
        String email = emailtxt.getText().toString();
        String cpf = cpftxt.getText().toString();
        String nasc = nasctxt.getText().toString();
        String end = endtxt.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> associados = new HashMap<>();
        associados.put("nome",nome);
        associados.put("fone",fone);
        associados.put("rg",rg);
        associados.put("senha",senha);
        associados.put("email",email);
        associados.put("cpf",cpf);
        associados.put("nasc",nasc);
        associados.put("end",end);

        associadoId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("associados").document(associadoId);
        documentReference.set(associados).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","Sucesso ao salvar os dados");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error","Erro ao salvar os dados" + e.toString());
            }
        });
    }

    private void IniciarComponentes(){
        nometxt = findViewById(R.id.nometxt);
        fonetxt = findViewById(R.id.fonetxt);
        emailtxt = findViewById(R.id.emailtxt);
        senhatxt = findViewById(R.id.senhatxt);
        rgtxt = findViewById(R.id.rgtxt);
        cpftxt = findViewById(R.id.cpftxt);
        nasctxt = findViewById(R.id.nasctxt);
        endtxt = findViewById(R.id.endtxt);

        salvarbtn = findViewById(R.id.salvarbtn);
    }

}