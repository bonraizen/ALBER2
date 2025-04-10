package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private DatabaseReference database;
    Button pindah;

    private EditText email_input,password_input;
    private Button signin;

    public void masukan(){
        pindah = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.sign_in_button);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        masukan();

        pindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsignup = new Intent(Login.this,SignUp.class);
                startActivity(pindahsignup);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_input.getText().toString().replace(".", "_");
                String password = password_input.getText().toString().replace("|","_");

                database = FirebaseDatabase.getInstance().getReference("user");

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(email)) {
                                String dbPassword = snapshot.child(email).child("password").getValue(String.class);
                                if (dbPassword != null && dbPassword.equals(password)) {
                                    Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent signini = new Intent(Login.this, beranda.class);
                                    startActivity(signini);
                                } else {
                                    Toast.makeText(Login.this, "Password salah", Toast.LENGTH_SHORT).show();
                                    System.out.println("DEBUG: Password yang dimasukkan tidak sesuai dengan database.");
                                }
                            } else {
                                Toast.makeText(Login.this, "Email belum terdaftar", Toast.LENGTH_SHORT).show();
                                System.out.println("DEBUG: Email tidak ditemukan di database.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Login.this, "Gagal mengakses database: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println("DEBUG: Firebase error - " + error.getMessage());
                        }
                    });
                }
            }
        });
    }
}