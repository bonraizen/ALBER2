package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.alber2.databinding.ActivityRollerBinding;

public class roller extends AppCompatActivity {

    Button btnexkobel;

    private void sumber(){
        btnexkobel = findViewById(R.id.btnexkobel);
    }

    private void move(){
        btnexkobel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(roller.this, Login.class);
                startActivity(detail);
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