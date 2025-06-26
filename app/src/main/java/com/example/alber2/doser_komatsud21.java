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

public class doser_komatsud21 extends AppCompatActivity {

    ImageButton btnback;

    Button btnreqorder;

    private void sumber(){
        btnback = findViewById(R.id.btnback);
        btnreqorder = findViewById(R.id.btnreqorder);
    }

    private void move(){
    btnback.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent back = new Intent(doser_komatsud21.this, dozer.class);
            startActivity(back);
            finish();
        }
    });

    btnreqorder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent order = new Intent(doser_komatsud21.this, Reqorder.class);
            startActivity(order);
            finish();
        }
    });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dozer);





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}
