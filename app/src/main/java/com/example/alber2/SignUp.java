package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth mAuth; // Tambahkan instance FirebaseAuth

    Button btnlogin;
    private Button btnsignup;

    private EditText etnama, etnamaPerusahaan, etnomorTelepon, etemail, etnikKTP, etcreatePassword, etconfirmPassword;

    public void sumber() {
        btnlogin = findViewById(R.id.sign_in_button);
        btnsignup = findViewById(R.id.btnSignUp);
        etnama = findViewById(R.id.etNama);
        etnamaPerusahaan = findViewById(R.id.etNamaPerusahaan);
        etnomorTelepon = findViewById(R.id.etNomorTelepon);
        etemail = findViewById(R.id.etEmail);
        etnikKTP = findViewById(R.id.etNikKtp);
        etcreatePassword = findViewById(R.id.etPassword);
        etconfirmPassword = findViewById(R.id.etConfirmPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance(); // Inisialisasi FirebaseAuth
        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sewa-alber-default-rtdb.firebaseio.com/");

        sumber();

        btnlogin.setOnClickListener(v -> {
            Intent pindahlogin = new Intent(SignUp.this, Login.class);
            startActivity(pindahlogin);
        });

        btnsignup.setOnClickListener(v -> {
            String nama = etnama.getText().toString();
            String namaPerusahaan = etnamaPerusahaan.getText().toString();
            String nomorTelepon = etnomorTelepon.getText().toString();
            String email = etemail.getText().toString();
            String nikKTP = etnikKTP.getText().toString();
            String createPassword = etcreatePassword.getText().toString();
            String confirmPassword = etconfirmPassword.getText().toString();

            if (nama.isEmpty() || createPassword.isEmpty() || confirmPassword.isEmpty() || nomorTelepon.isEmpty() || nikKTP.isEmpty() ) {
                Toast.makeText(SignUp.this, "Tidak boleh ada form yang kosong", Toast.LENGTH_SHORT).show();
            } else if (!createPassword.equals(confirmPassword)) {
                Toast.makeText(SignUp.this, "Konfirmasi password tidak sesuai", Toast.LENGTH_SHORT).show();
            } else {
                // Buat user baru di Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, createPassword)
                        .addOnCompleteListener(SignUp.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();

                                // Kirim email verifikasi
                                user.sendEmailVerification()
                                        .addOnCompleteListener(sendTask -> {
                                            if (sendTask.isSuccessful()) {
                                                // Simpan data pengguna ke Realtime Database
                                                database = FirebaseDatabase.getInstance().getReference("user");
                                                DatabaseReference userRef = database.child(email.replace(".", "_"));
                                                userRef.child("nama").setValue(nama);
                                                userRef.child("namaPerusahaan").setValue(namaPerusahaan);
                                                userRef.child("nomorTelepon").setValue(nomorTelepon);
                                                userRef.child("email").setValue(email);
                                                userRef.child("nikKTP").setValue(nikKTP);
                                                userRef.child("password").setValue(createPassword);

                                                Toast.makeText(SignUp.this, "Pendaftaran berhasil. Silakan periksa email Anda untuk verifikasi.",
                                                        Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(SignUp.this, Login.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // Gagal mengirim email verifikasi
                                                Toast.makeText(SignUp.this, "Gagal mengirim email verifikasi: " + sendTask.getException().getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                                Log.e("SignUpActivity", "Gagal mengirim verifikasi email", sendTask.getException());
                                            }
                                        });
                            } else {
                                // Jika pendaftaran gagal, tampilkan pesan error.
                                Toast.makeText(SignUp.this, "Pendaftaran gagal: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                Log.e("SignUpActivity", "Pendaftaran user gagal", task.getException());
                            }
                        });
            }
        });
    }
}