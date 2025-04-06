package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button startButton,loginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        loginButton = findViewById(R.id.loginButton);

        startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindahsignup = new Intent(MainActivity.this,SignUp.class);
                    startActivity(pindahsignup);
                }
            });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahlogin = new Intent(MainActivity.this,Login.class);
                startActivity(pindahlogin);
            }
        });
    }
}