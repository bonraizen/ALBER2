package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class dtMiniExc5tn_kblco extends AppCompatActivity {

    Button btnreqorder,btncekkuota;


    public void sumber(){
    btnreqorder = findViewById(R.id.btnreqorder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dt_mini_exc5tn_kblco);

        sumber();

        btnreqorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reqorder = new Intent(dtMiniExc5tn_kblco.this,Reqorder.class);
                startActivity(reqorder);
            }
        });
    }
    }