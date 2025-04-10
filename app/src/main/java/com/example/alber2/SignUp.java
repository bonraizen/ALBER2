package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    private DatabaseReference database;

    Button btnlogin;
    private Button btnsignup;

    private EditText etnama,etnamaPerusahaan,etnomorTelepon,etemail,etnikKTP,etcreatePassword,etconfirmPassword;

    public void sumber(){
        btnlogin = findViewById(R.id.btnlogin);
        btnsignup =findViewById(R.id.btnsignup);
        etnama = findViewById(R.id.etnama);
        etnamaPerusahaan = findViewById(R.id.etnamaPerusahaan);
        etnomorTelepon = findViewById(R.id.etnomorTelepon);
        etemail = findViewById(R.id.etemail);
        etnikKTP = findViewById(R.id.etnikKTP);
        etcreatePassword = findViewById(R.id.etcreatePassword);
        etconfirmPassword = findViewById(R.id.etconfirmPassword);
    }
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sewa-alber-default-rtdb.firebaseio.com/");

        sumber();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahlogin = new Intent(SignUp.this,Login.class);
                startActivity(pindahlogin);
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etnama.getText().toString();
                String namaPerusahaan = etnamaPerusahaan.getText().toString();
                String nomorTelepon = etnomorTelepon.getText().toString();
                String email = etemail.getText().toString();
                String nikKTP = etnikKTP.getText().toString();
                String createPassword = etcreatePassword.getText().toString();
                String confirmPassword = etconfirmPassword.getText().toString();

                if (nama.isEmpty() || createPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUp.this, "Tidak boleh ada form yang kosong", Toast.LENGTH_SHORT).show();
                } else {
                    database = FirebaseDatabase.getInstance().getReference("user");

                    DatabaseReference userRef = database.child(email.replace(".", "_")); // Firebase tidak mendukung titik di key
                    userRef.child("nama").setValue(nama)
                            .addOnSuccessListener(aVoid -> {
                                userRef.child("namaPerusahaan").setValue(namaPerusahaan);
                                userRef.child("email").setValue(email);
                                userRef.child("password").setValue(createPassword);

                                Toast.makeText(SignUp.this, "Data Berhasil Disimpan: " + nama, Toast.LENGTH_SHORT).show();
                                Intent sini = new Intent(SignUp.this, Login.class);
                                startActivity(sini);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(SignUp.this, "Gagal menyimpan data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                e.printStackTrace(); // Untuk logcat debugging
                            });
                }
            }
        });

    }
}