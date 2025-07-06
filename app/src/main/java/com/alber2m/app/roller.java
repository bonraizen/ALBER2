package com.alber2m.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.alber2m.app.databinding.ActivityRollerBinding;

public class roller extends AppCompatActivity {

    Button btnexkobel;
    ImageButton btnback;

    private void sumber(){
        btnexkobel = findViewById(R.id.btnexkobel);
        btnback = findViewById(R.id.btnback);
    }

    private void move(){
        btnexkobel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(roller.this, vibro_roller.class);
                startActivity(detail);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(roller.this, beranda.class);
                startActivity(back);
            }
        });

    }
//111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_roller);

        sumber();
        move();

    }
}