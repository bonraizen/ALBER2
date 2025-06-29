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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.firebase.auth.FirebaseAuth;

public class beranda extends AppCompatActivity {

    ImageButton btnexca,btnroller,btndozer,btnbreakerexca,btnforklift,btnlift,btnprofiluser;

    private AdView mAdView;
    public void sumber (){
        btnexca = findViewById(R.id.btnexca);
        btnroller = findViewById(R.id.btnroller);
        btndozer = findViewById(R.id.btndozer);
        btnbreakerexca = findViewById(R.id.btnbreakerexca);
        btnforklift = findViewById(R.id.btnforklift);
        btnlift = findViewById(R.id.btnlift);
        btnprofiluser = findViewById(R.id.btnprofiluser);
        mAdView = findViewById(R.id.adView);
    }

    public void move() {
        btnexca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eca = new Intent(beranda.this,bucketexca.class);
                startActivity(eca);
            }
        });

        btnroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent roller = new Intent(beranda.this, com.example.alber2.roller.class);
                startActivity(roller);
            }
        });

        btnprofiluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent usr = new Intent( beranda.this,profileuser.class);
                startActivity(usr);
            }
        });

        btnbreakerexca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent breaker = new Intent( beranda.this, breakexca.class);
                startActivity(breaker);
            }
        });

        btnforklift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forklif = new Intent(beranda.this, forklift.class);
                startActivity(forklif);
            }
        });

        btndozer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btndozer = new Intent(beranda.this, dozer.class);
                startActivity(btndozer);
            }
        });

        btnlift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnlift = new Intent(beranda.this, scissor_lift.class);
                startActivity(btnlift);
            }
        });
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_beranda);

        //Masukan reference tombol
            sumber();
            //Masukan event click
        move();

           AdRequest adRequest = new AdRequest.Builder().build();
           mAdView.loadAd(adRequest);

    }
}