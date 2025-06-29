package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class dtexca5tn_cat extends AppCompatActivity {

    //Button initial
    ImageButton btnback;

    Button btnreqorder,cekstok;

    EditText ettersedia ;

    private void sumber() {
        btnback = findViewById(R.id.btnback);
        btnreqorder = findViewById(R.id.btnreqorder);
        cekstok =findViewById(R.id.cekstok);
        ettersedia = findViewById(R.id.ettersedia);
    }

    private void move(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(dtexca5tn_cat.this,bucketexca.class);
                startActivity(back);

            }
        });

        btnreqorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent(dtexca5tn_cat.this,Reqorder.class);
                startActivity(order);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dt_exca5tn_cat);

        //reference
        sumber();

        //onclicklistener
        move();

    }
}
