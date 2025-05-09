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

    Button detexcatnklbco;

    public void sumber(){
        detexcatnklbco = findViewById(R.id.btnexkobel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bucketexca);

        sumber();

        detexcatnklbco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detilexcatnklbco = new Intent(bucketexca.this,dtMiniExc5tn_kblco.class);
                startActivity(detilexcatnklbco);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}