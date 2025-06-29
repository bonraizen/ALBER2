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

public class dozer extends AppCompatActivity {

    Button btnexkobel,btndozerdet;
    ImageButton btnback;
    private void sumber(){
        btnexkobel = findViewById(R.id.btnexkobel);
        btnback = findViewById(R.id.btnback);
        btndozerdet = findViewById(R.id.btndozerdet);
    }

    private void move(){
        btnexkobel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detkobel = new Intent(dozer.this,doser_komatsu.class);
                startActivity(detkobel);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(dozer.this, beranda.class);
                startActivity(back);
            }
        });

        btndozerdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detdozer = new Intent(dozer.this, doser_komatsud21.class);
                startActivity(detdozer);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dozer);

        //referensi
        sumber();

        //event clik
        move();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}