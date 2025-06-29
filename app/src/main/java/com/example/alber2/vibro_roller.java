package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class vibro_roller extends AppCompatActivity{

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
                Intent back = new Intent(vibro_roller.this,roller.class);
                startActivity(back);
            }
        });

        btnreqorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(vibro_roller.this,Reqorder.class);
                startActivity(order);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vibro_roller);


        sumber();
        move();
    }
}
