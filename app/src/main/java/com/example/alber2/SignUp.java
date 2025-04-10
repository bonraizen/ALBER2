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

                if(nama.isEmpty()||namaPerusahaan.isEmpty()||nomorTelepon.isEmpty()||email.isEmpty()||nikKTP.isEmpty()||createPassword.isEmpty()||confirmPassword.isEmpty()) {
                    Toast.makeText(SignUp.this, "Tidak boleh ada form yang kosong", Toast.LENGTH_SHORT).show();

                }else{
                        database = FirebaseDatabase.getInstance().getReference("user");
                        database.child(email).child("nama").setValue(nama);
                        database.child(email).child("namaPerusahaan").setValue(namaPerusahaan);
                        database.child(email).child("nomorTelepon").setValue(nomorTelepon);
                        database.child(email).child("email").setValue(email);
                        database.child(email).child("nikKTP").setValue(nikKTP);
                        database.child(email).child("password").setValue(confirmPassword);
                        Toast.makeText(SignUp.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                        Intent login = new Intent(SignUp.this,Login.class);
                        startActivity(login);
                    }

            }
        });
    }
}