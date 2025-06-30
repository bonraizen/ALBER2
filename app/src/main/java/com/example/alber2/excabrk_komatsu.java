package com.example.alber2;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class excabrk_komatsu extends AppCompatActivity {

    ImageButton btnback;
    Button btnreqorder,cekstok;

    EditText ettersedia;

    private void sumber(){
        btnback = findViewById(R.id.btnback);
        btnreqorder = findViewById(R.id.btnreqorder);
        ettersedia = findViewById(R.id.ettersedia);
        cekstok = findViewById(R.id.cekstok);
    }

    private void move(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(excabrk_komatsu.this, breakexca.class);
                startActivity(back);
            }
        });

        btnreqorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(excabrk_komatsu.this,Reqorder.class);
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
        String inisial = "excabrk_komatsu";
        db = FirebaseDatabase.getInstance().getReference("Stok").child(inisial);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {
                        String cekstok = snapshot.child("stok").getValue(String.class);
                        ettersedia.setText(cekstok != null ? cekstok : "Maaf Stok Kosong");
                    } catch (Exception e) {
                        Toast.makeText(excabrk_komatsu.this, "Eror saat: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(excabrk_komatsu.this, "EROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excavator_komatsu);

        //Referensi
        sumber();

        //eventclicklisterner
        move();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
