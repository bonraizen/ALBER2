package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileuser extends AppCompatActivity {

    private TextView Nama, Korporat, Email,Notlp;

    private Button back_button;
    private DatabaseReference database; // Referensi khusus untuk nama
    private FirebaseUser currentUser;


    private void sumber() {
        //Element Edit Text
        Nama = findViewById(R.id.Nama);
        Korporat = findViewById(R.id.korporat);
        Email = findViewById(R.id.email);
        Notlp = findViewById(R.id.notlp);

        //button
        back_button = findViewById(R.id.back_button);
    }

    private void Move (){
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move_back = new Intent(profileuser.this, beranda.class);
                startActivity(move_back);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profileuser);

        Move();
        sumber();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // Mendapatkan UID dari user yang sedang login
            String userId = currentUser.getUid();

            // Mendapatkan referensi ke node 'users' di database Firebase
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            database = firebaseDatabase.getReference("users").child(userId);

            // Menambahkan ValueEventListener untuk membaca data dari database
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // DataSnapshot berisi data dari lokasi database yang direferensikan

                    // Mendapatkan nilai untuk nama, email, korporat, dan telepon jika ada
                    User_manage nama = dataSnapshot.child("nama").getValue(User_manage.class);
                    User_manage email = dataSnapshot.child("email").getValue(User_manage.class);
                    User_manage namaPerusahaan = dataSnapshot.child("namaPerusahaan").getValue(User_manage.class);
                    User_manage tlp = dataSnapshot.child("tlp").getValue(User_manage.class);

                    // Memasukkan nilai yang didapatkan ke dalam TextView
                    if (nama != null) {
                        Nama.setText((CharSequence) nama);
                    } else {
                        Nama.setText("-"); // Atau nilai default lainnya
                    }

                    if (email != null) {
                        Email.setText((CharSequence) email);
                    } else {
                        Email.setText("-");
                    }

                    if (namaPerusahaan != null) {
                        Korporat.setText((CharSequence) namaPerusahaan);
                    } else {
                        Korporat.setText("-");
                    }

                    if (tlp != null) {
                        Notlp.setText((CharSequence) tlp);
                    } else {
                        Notlp.setText("-");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Terjadi error saat membaca data
                    Log.w("profileuser", "Failed to read user data.", databaseError.toException());
                    // Handle error sesuai kebutuhan aplikasi Anda
                }
            });
        } else {
            // Jika tidak ada user yang login, mungkin kembali ke halaman login atau menampilkan pesan error
            Log.d("profileuser", "No current user found.");
            startActivity(new Intent(this,Login.class));
            finish();
        }

    }
}