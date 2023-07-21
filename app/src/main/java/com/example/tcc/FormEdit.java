package com.example.tcc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FormEdit<onStart> extends AppCompatActivity {

    Button updateBt;
    EditText nometxt, fonetxt, rgtxt, cpftxt, nasctxt, endtxt, associadoEmail;
    FirebaseFirestore db;
    String associadoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);

        getSupportActionBar().hide();

        IniciarComponentes();

        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nometxt.getText().toString();
                String fone = fonetxt.getText().toString();
                String end = endtxt.getText().toString();
                String nasc = nasctxt.getText().toString();
                String rg = rgtxt.getText().toString();
                String cpf = cpftxt.getText().toString();
                String email = associadoEmail.getText().toString();
                UpdateDados(email, nome, fone, end, nasc, rg, cpf);

            }

        });
    }

    private void UpdateDados(String email, String nome, String fone, String end, String nasc, String rg, String cpf) {
        Map<String,Object> assocDetail = new HashMap<>();
        assocDetail.put("nome", nome);
        assocDetail.put("fone", fone);
        assocDetail.put("rg", rg);
        assocDetail.put("cpf", cpf);
        assocDetail.put("end", end);
        assocDetail.put("nasc", nasc);

        db.collection("associados").
                whereEqualTo("email",email).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){

                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentId = documentSnapshot.getId();
                    db.collection("associados").document(documentId).update(assocDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(FormEdit.this, "Sucesso ao atualizar dados", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(FormEdit.this, "Ocorreu algum erro ao atualizar dados", Toast.LENGTH_SHORT).show();

                        }
                    });

                }else{

                    Toast.makeText(FormEdit.this, "Ocorreu algum erro", Toast.LENGTH_SHORT).show();

                }
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
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    associadoEmail.setText(email);
                    nometxt.setText(value.getString("nome"));
                    fonetxt.setText(value.getString("fone"));
                    rgtxt.setText(value.getString("rg"));
                    cpftxt.setText(value.getString("cpf"));
                    endtxt.setText(value.getString("end"));
                    nasctxt.setText(value.getString("nasc"));
                }
            }
        });

    }

    private void IniciarComponentes(){
        db = FirebaseFirestore.getInstance();

        updateBt = findViewById(R.id.salvarbtn2);

        associadoEmail = findViewById(R.id.emailassoc);
        nometxt = findViewById(R.id.nometxt2);
        fonetxt = findViewById(R.id.fonetxt2);
        rgtxt = findViewById(R.id.rgtxt2);
        cpftxt = findViewById(R.id.cpftxt2);
        nasctxt = findViewById(R.id.nasctxt2);
        endtxt = findViewById(R.id.endtxt2);
    }
}