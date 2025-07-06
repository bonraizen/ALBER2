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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class excabrk_kubota extends AppCompatActivity {

    ImageButton btnback;

    EditText ettersedia;

    Button btnreqorder;

    private void sumber(){
        btnback = findViewById(R.id.btnback);
        btnreqorder = findViewById(R.id.btnreqorder);
        ettersedia = findViewById(R.id.ettersedia);
    }

    private void move(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(excabrk_kubota.this, breakexca.class);
                startActivity(back);
            }
        });

        btnreqorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(excabrk_kubota.this, Reqorder.class);
                startActivity(order);
            }
        });
    }

    private void setCekstok(){
        DatabaseReference db;
        String inisial = "excabrk_kubota";
        db = FirebaseDatabase.getInstance().getReference("Stok").child(inisial);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {
                        String cekstok = snapshot.child("stok").getValue(String.class);
                        ettersedia.setText(cekstok != null ? cekstok : "Maaf Stok Kosong");
                    } catch (Exception e) {
                        Toast.makeText(excabrk_kubota.this, "Eror saat: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(excabrk_kubota.this, "EROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activiy_excavator_kubota);

        //Referensi
        sumber();

        setCekstok();

        //eventclicklisterner
        move();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
