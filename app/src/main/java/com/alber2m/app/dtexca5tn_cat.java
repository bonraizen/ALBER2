package com.alber2m.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dtexca5tn_cat extends AppCompatActivity {

    //Button initial
    ImageButton btnback;

    Button btnreqorder,cekstok;

    EditText ettersedia ;

    private void sumber() {
        btnback = findViewById(R.id.btnback);
        btnreqorder = findViewById(R.id.btnreqorder);
        cekstok =findViewById(R.id.cekstok);
        ettersedia = findViewById(R.id.ettersedia);
    }

    private void move(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(dtexca5tn_cat.this,bucketexca.class);
                startActivity(back);

            }
        });

        btnreqorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(dtexca5tn_cat.this,Reqorder.class);
                startActivity(order);
            }
        });

        cekstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCekstok();
            }
        });
    }

    private void setCekstok(){
        DatabaseReference db;
        String inisial = "exca_5tn_cat";
        db = FirebaseDatabase.getInstance().getReference("Stok").child(inisial);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {
                        String cekstok = snapshot.child("stok").getValue(String.class);
                        ettersedia.setText(cekstok != null ? cekstok : "Maaf Stok Kosong");
                    } catch (Exception e) {
                        Toast.makeText(dtexca5tn_cat.this, "Eror saat: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(dtexca5tn_cat.this, "EROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dt_exca5tn_cat);

        //reference
        sumber();

        //onclicklistener
        move();

    }
}
