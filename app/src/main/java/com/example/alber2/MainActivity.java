package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button pindah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

            pindah = findViewById(R.id.startButton);

            pindah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindahsignup = new Intent(MainActivity.this,SignUp.class);
                    startActivity(pindahsignup);
                }
            });

        pindah = findViewById(R.id.loginButton);
        pindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahlogin = new Intent(MainActivity.this,Login.class);
                startActivity(pindahlogin);
            }
        });
    }
}