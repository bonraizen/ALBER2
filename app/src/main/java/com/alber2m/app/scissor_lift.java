package com.alber2m.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class scissor_lift  extends AppCompatActivity {

    ImageButton btnback;

    Button btnexkobel;

    private void sumber(){
        btnback = findViewById(R.id.btnback);
        btnexkobel = findViewById(R.id.btnexkobel);
    }

    private void move(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent back = new Intent(scissor_lift.this,beranda.class);
               startActivity(back);
            }
        });

        btnexkobel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dtsiccor = new Intent(scissor_lift.this, dtscissor_lift.class);
                startActivity(dtsiccor);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scissor_lift);

        //Reference
        sumber();
        //clicklistener
        move();


    }

}
