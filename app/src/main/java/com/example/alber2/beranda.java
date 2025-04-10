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

public class beranda extends AppCompatActivity {

    ImageButton btnexca,btnroller,btndozer,btnbreakerexca,btnforklift,btnlift;


    public void sumber (){
        btnexca = findViewById(R.id.btnexca);
        btnroller = findViewById(R.id.btnroller);
        btndozer = findViewById(R.id.btndozer);
        btnbreakerexca = findViewById(R.id.btnbreakerexca);
        btnforklift = findViewById(R.id.btnforklift);
        btnlift = findViewById(R.id.btnlift);
    }

    public void exca  () {
        Intent pindahexca = new Intent(beranda.this,bucketexca.class);
        startActivity(pindahexca);
    };
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



    }
}