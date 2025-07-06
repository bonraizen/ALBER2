package com.alber2m.app;

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

public class bucketexca extends AppCompatActivity {

    Button detexcatnklbco,btnexcat,btnexkmts;
    ImageButton btnback;

    public void sumber(){
        btnexcat = findViewById(R.id.btnexcat);
        btnexkmts = findViewById(R.id.btnexkmts);
        detexcatnklbco = findViewById(R.id.btnexkobel);
        btnback = findViewById(R.id.btnback);
    }

    private void move(){
        detexcatnklbco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detilexcatnklbco = new Intent(bucketexca.this,dtMiniExc5tn_kblco.class);
                startActivity(detilexcatnklbco);
            }
        });

        btnexcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cat = new Intent(bucketexca.this,dtexca5tn_cat.class);
                startActivity(cat);
            }
        });

        btnexkmts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent komatsu = new Intent(bucketexca.this,dtexcat5tn_comatsu.class);
                startActivity(komatsu);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(bucketexca.this, beranda.class);
                startActivity(back);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bucketexca);

        sumber();

        move();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}