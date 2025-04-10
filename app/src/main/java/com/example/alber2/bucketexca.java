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

    Button DetExca5tnKlbe,DetExca5tnCat,DetExca5tnKmtsu;

    public void sumber(){
        DetExca5tnKlbe = findViewById(R.id.DetExca5tnKlbe);
        DetExca5tnCat = findViewById(R.id.DetExca5tnCat);
        DetExca5tnKmtsu = findViewById(R.id.DetExca5tnKmtsu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bucketexca);

        sumber();

        DetExca5tnKlbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detexca5tnklbco = new Intent(getApplicationContext(),dtMiniExc5tn_kblco.class);
                startActivity(detexca5tnklbco);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}