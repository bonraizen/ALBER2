package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class bucketexca extends AppCompatActivity {

    Button detexca5tnklbco,DetExca5tnCat,DetExca5tnKmtsu;

    public void sumber(){
        detexca5tnklbco = findViewById(R.id.detexca5tnklbco);
        DetExca5tnCat = findViewById(R.id.DetExca5tnCat);
        DetExca5tnKmtsu = findViewById(R.id.DetExca5tnKmtsu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bucketexca);

        sumber();

        detexca5tnklbco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detilexca5tnklbco = new Intent(bucketexca.this,dtMiniExc5tn_kblco.class);
                startActivity(detilexca5tnklbco);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}