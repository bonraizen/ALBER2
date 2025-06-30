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

public class breakexca extends AppCompatActivity {

    Button btnexkmts,btnexkbuta;

    ImageButton btnback;

    private void sumber(){
        btnexkmts = findViewById(R.id.btnexkmts);
        btnback = findViewById(R.id.btnback);
        btnexkbuta = findViewById(R.id.btnexkbuta);
    }

    private void move(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(breakexca.this, beranda.class);
                startActivity(back);

            }
        });

        btnexkmts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kometsu = new Intent(breakexca.this, excabrk_komatsu.class);
                startActivity(kometsu);

            }
        });

        btnexkbuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kubota = new Intent(breakexca.this, excabrk_kubota.class);
                startActivity(kubota);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_breakexca);

        //Referensi
        sumber();

        //eventclic listener
        move();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}