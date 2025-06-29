package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class doser_komatsu extends AppCompatActivity {

    Button btnreqorder;

    ImageButton btnback;

    private void sumber(){
        btnreqorder = findViewById(R.id.btnreqorder);
        btnback = findViewById(R.id.btnback);
    }

    private void move(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(doser_komatsu.this,dozer.class);
                startActivity(back);
            }
        });

        btnreqorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  order = new Intent(doser_komatsu.this,Reqorder.class);
                startActivity(order);
            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doser_komatsu);

        sumber();

        move();

    }
}
