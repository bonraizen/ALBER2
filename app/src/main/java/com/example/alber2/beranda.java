package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class beranda extends AppCompatActivity {

    ImageButton btnexca,btnroller,btndozer,btnbreakerexca,btnforklift,btnlift,btnprofiluser;


    public void sumber (){
        btnexca = findViewById(R.id.btnexca);
        btnroller = findViewById(R.id.btnroller);
        btndozer = findViewById(R.id.btndozer);
        btnbreakerexca = findViewById(R.id.btnbreakerexca);
        btnforklift = findViewById(R.id.btnforklift);
        btnlift = findViewById(R.id.btnlift);
        btnprofiluser = findViewById(R.id.btnprofiluser);
    }

    public void exca  () {
        Intent pindahexca = new Intent(beranda.this,bucketexca.class);
        startActivity(pindahexca);
    };

    public void dtexca(){
        Intent dt = new Intent( beranda.this,MainActivity.class);
        startActivity(dt);
    }

    public void profuser(){
        Intent usr = new Intent( beranda.this,profileuser.class);
        startActivity(usr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_beranda);

        sumber();

        btnexca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exca();
            }
        });

        btnroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dtexca();
                finish();
            }
        });

        btnprofiluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profuser();
            }
        });




    }
}