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

public class doser_komatsu extends AppCompatActivity {

    Button btnreqorder;

    ImageButton btnback;

    EditText ettersedia;

    private void sumber(){
        btnreqorder = findViewById(R.id.btnreqorder);
        btnback = findViewById(R.id.btnback);
        ettersedia = findViewById(R.id.ettersedia);
    }

    private void move(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(doser_komatsu.this,dozer.class);
                startActivity(back);
            }
        });

        btnreqorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  order = new Intent(doser_komatsu.this,Reqorder.class);
                startActivity(order);
            }
        });

    }

    private void setCekstok(){
        DatabaseReference db;
        String inisial = "dozer_komatsu";
        db = FirebaseDatabase.getInstance().getReference("Stok").child(inisial);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {
                        String cekstok = snapshot.child("stok").getValue(String.class);
                        ettersedia.setText(cekstok != null ? cekstok : "Maaf Stok Kosong");
                    } catch (Exception e) {
                        Toast.makeText(doser_komatsu.this, "Eror saat: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(doser_komatsu.this, "EROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doser_komatsu);

        sumber();

        setCekstok();

        move();

    }
}
