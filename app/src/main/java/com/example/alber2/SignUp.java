package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth mAuth; // Tambahkan instance FirebaseAuth
    private FirebaseUser currentUser;

    Button btnlogin;
    private Button btnsignup;
    TextView tvjudul,tv_creatpass,tv_conpass,tv_showpass,quest,tvSignIn;
    private EditText etnama, etnamaPerusahaan, etnomorTelepon, etemail, etnikKTP, etcreatePassword, etconfirmPassword;

    public void sumber() {
        tvjudul = findViewById(R.id.tvjudul);
        tv_creatpass =findViewById(R.id.tv_creatpass);
        tv_conpass = findViewById(R.id.tv_conpass);
        tv_showpass = findViewById(R.id.tv_showpass);
        tvSignIn = findViewById(R.id.tvSignIn);
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
        currentUser = mAuth.getCurrentUser();
        sumber();

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignUp.this,Login.class);
                startActivity(login);
            }
        });

        tv_showpass.setOnClickListener(v -> {
            boolean passwordVisible = (etcreatePassword.getTransformationMethod() == null);
            if (passwordVisible) {
                // Sembunyikan password
                etcreatePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                // Tampilkan password
                etcreatePassword.setTransformationMethod(null);
            }

            // Pindahkan kursor ke akhir teks agar tetap terlihat saat perubahan
            etcreatePassword.setSelection(etcreatePassword.getText().length());
        });


        if (currentUser != null) {
            tvjudul.setText("EDIT PROFIL");
            btnsignup.setText("Kirim");
            tv_creatpass.setText("Buat Sandi Baru");
            tv_conpass.setText("Konfirmasi Sandi Baru");
            etemail.setEnabled(false); // Buat email tidak bisa diedit

            // Muat data pengguna dari Firebase Realtime Database
            String userEmailKey = currentUser.getEmail().replace(".", "_");
            DatabaseReference userRef = database.child("user").child(userEmailKey);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Isi kolom EditText dengan data yang ada di database
                        etnama.setText(snapshot.child("nama").getValue(String.class));
                        etnamaPerusahaan.setText(snapshot.child("namaPerusahaan").getValue(String.class));
                        etnomorTelepon.setText(snapshot.child("nomorTelepon").getValue(String.class));
                        etemail.setText(snapshot.child("email").getValue(String.class));
                        etnikKTP.setText(snapshot.child("nikKTP").getValue(String.class));
                        etcreatePassword.setText(snapshot.child("password").getValue(String.class));
                        etconfirmPassword.setText(snapshot.child("password").getValue(String.class));
                    } else {
                        Toast.makeText(SignUp.this, "Data profil tidak ditemukan.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("SignUpActivity", "Gagal membaca data profil", error.toException());
                    Toast.makeText(SignUp.this, "Gagal memuat data profil.", Toast.LENGTH_SHORT).show();
                }
            });
        }


        btnsignup.setOnClickListener(v -> {
            if (currentUser != null){
                String nama = etnama.getText().toString();
                String namaPerusahaan = etnamaPerusahaan.getText().toString();
                String nomorTelepon = etnomorTelepon.getText().toString();
                String nikKTP = etnikKTP.getText().toString();
                String newPassword = etcreatePassword.getText().toString();
                String confirmNewPassword = etconfirmPassword.getText().toString();

                //validasi input
                if (nama.isEmpty() || nomorTelepon.isEmpty() || nikKTP.isEmpty()) {
                    Toast.makeText(SignUp.this, "Nama, Nomor Telepon, dan NIK KTP tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return; // Hentikan proses jika ada field yang kosong
                }

                if (!newPassword.isEmpty() || !confirmNewPassword.isEmpty()) {
                    if (!newPassword.equals(confirmNewPassword)) {
                        Toast.makeText(SignUp.this, "Konfirmasi kata sandi baru tidak sesuai", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (newPassword.length() < 6) { // Firebase password minimum length
                        Toast.makeText(SignUp.this, "Kata sandi baru minimal 6 karakter", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // --- Start Reauthentication Process ---
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                builder.setTitle("Verifikasi Identitas");
                builder.setMessage("Untuk memperbarui profil Anda, harap masukkan kata sandi Anda saat ini.");

                final EditText input = new EditText(SignUp.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                input.setTransformationMethod(PasswordTransformationMethod.getInstance()); // Ensure password dots
                LinearLayout layout = new LinearLayout(SignUp.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50, 20, 50, 0); // Add padding
                layout.addView(input);
                builder.setView(layout);

                builder.setPositiveButton("OK", (dialog, which) -> {
                    String currentPassword = input.getText().toString();
                    if (currentPassword.isEmpty()) {
                        Toast.makeText(SignUp.this, "Kata sandi saat ini tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        return;
                    }


                        // Reauthenticate user
                        AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);
                        currentUser.reauthenticate(credential)
                                .addOnCompleteListener(reauthTask -> {
                                    if (reauthTask.isSuccessful()) {
                                        Log.d("SignUpActivity", "Reauthentication successful.");
                                    }
                                    String userEmailKey = currentUser.getEmail().replace(".", "_");
                                    DatabaseReference userRef = database.child("user").child(userEmailKey);

                                    // Perbarui setiap field secara individual
                                    userRef.child("nama").setValue(nama);
                                    userRef.child("namaPerusahaan").setValue(namaPerusahaan);
                                    userRef.child("nomorTelepon").setValue(nomorTelepon);
                                    userRef.child("nikKTP").setValue(nikKTP)
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    // Jika kata sandi baru diberikan, perbarui kata sandi di Firebase Authentication
                                                    if (!newPassword.isEmpty() && !confirmNewPassword.isEmpty()) {
                                                        if (newPassword.equals(confirmNewPassword)) {
                                                            currentUser.updatePassword(newPassword)
                                                                    .addOnCompleteListener(passwordTask -> {
                                                                        if (passwordTask.isSuccessful()) {
                                                                            userRef.child("password").setValue(newPassword); // Perbarui kata sandi di Realtime DB
                                                                            Intent intent = new Intent(SignUp.this, profileuser.class);
                                                                            startActivity(intent);
                                                                            Toast.makeText(SignUp.this, "Profil dan kata sandi berhasil diperbarui.", Toast.LENGTH_LONG).show();
                                                                            finish();
                                                                        } else {
                                                                            Toast.makeText(SignUp.this, "Gagal memperbarui kata sandi: " + passwordTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                            Log.e("SignUpActivity", "Pembaruan kata sandi gagal", passwordTask.getException());
                                                                        }
                                                                    });
                                                        } else {
                                                            Toast.makeText(SignUp.this, "Konfirmasi kata sandi baru tidak sesuai", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(SignUp.this, "Profil berhasil diperbarui.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(SignUp.this, "Gagal memperbarui profil: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    Log.e("SignUpActivity", "Pembaruan profil gagal", task.getException());
                                                }
                                            });
                                });
                });
                builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
                builder.show();

            }else {
                String nama = etnama.getText().toString();
                String namaPerusahaan = etnamaPerusahaan.getText().toString();
                String nomorTelepon = etnomorTelepon.getText().toString();
                String email = etemail.getText().toString();
                String nikKTP = etnikKTP.getText().toString();
                String createPassword = etcreatePassword.getText().toString();
                String confirmPassword = etconfirmPassword.getText().toString();

                if (nama.isEmpty() || createPassword.isEmpty() || confirmPassword.isEmpty() || nomorTelepon.isEmpty() || nikKTP.isEmpty()) {
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
            }
        });
    }
}