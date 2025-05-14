package com.example.alber2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileuser extends AppCompatActivity {

    private TextView Nama, korporat, email;
    private DatabaseReference database; // Referensi khusus untuk nama


    private void sumber() {
        Nama = findViewById(R.id.Nama);
        korporat = findViewById(R.id.korporat);
        email = findViewById(R.id.email);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profileuser);
        sumber();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        database = firebaseDatabase.getReference("user");
        database = database.child("nama");
        database = database.child("email");
        database = database.child("tlp");
    }
}