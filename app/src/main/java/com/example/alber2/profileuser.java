package com.example.alber2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileuser extends AppCompatActivity {

    private TextView Nama, Korporat, Email, Notlp, name_label;
    private Button btn_logout, btn_updateprofpeng, btn_Delakun;
    private DatabaseReference database; // Reference for user data
    private FirebaseUser currentUser;


    // Method to initialize UI elements
    private void sumber() {
        Nama = findViewById(R.id.Nama);
        Korporat = findViewById(R.id.korporat);
        Email = findViewById(R.id.email);
        name_label = findViewById(R.id.name_label);
        Notlp = findViewById(R.id.notlp);
        btn_logout = findViewById(R.id.btn_logout);
        btn_updateprofpeng = findViewById(R.id.btn_updateprofpeng);
        btn_Delakun = findViewById(R.id.btn_Delakun);
    }

    public void dbref(){

    }

    // Method to handle navigation back to beranda activity
    private void deletakun() {
        deleteUserDataAndAccount();
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent splas = new Intent(profileuser.this, MainActivity.class);
        startActivity(splas);
        finish();
    }

    public void update() {
        Intent update = new Intent(profileuser.this, SignUp.class);
        startActivity(update);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profileuser);
        dbref();
        sumber(); // Initialize UI elements
        setinfoprofil();


        // Set click listener for the back button
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(); // Navigate back
            }
        });

        btn_updateprofpeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });


        btn_Delakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletakun();
            }
        });

        // Apply window insets for edge-to-edge display (from original code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void setinfoprofil() {
        // Get the UID of the currently logged-in user
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        String email = currentUser.getEmail();
        String userId = email.replace(".", "_");
        database = FirebaseDatabase.getInstance().getReference("user").child(userId);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    try {
                        String email = datasnapshot.child("email").getValue(String.class);
                        String nama = datasnapshot.child("nama").getValue(String.class);
                        String namaPerusahaan = datasnapshot.child("namaPerusahaan").getValue(String.class);
                        String nomorTelepon = datasnapshot.child("nomorTelepon").getValue(String.class);
//                        String password = datasnapshot.child("password").getValue(String.class);
//                        String nikKTP = datasnapshot.child("nikKTP").getValue(String.class);

                        name_label.setText((nama != null ? nama : "-"));
                        Email.setText((email != null ? email : "-"));
                        Nama.setText((nama != null ? nama : "-"));
                        Korporat.setText((namaPerusahaan != null ? namaPerusahaan : "-"));
                        Notlp.setText((nomorTelepon != null ? nomorTelepon : "-"));
//                        tvPassword.setText("Password: " + (password != null ? password : "N/A"));
//                        tvNikKTP.setText("NIK KTP: " + (nikKTP != null ? nikKTP : "N/A"));
                    } catch (Exception e) {
                        // Tangani exception jika ada masalah saat mengambil atau mengkonversi data
                        Toast.makeText(profileuser.this, "Eror saat: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace(); // Cetak stack trace untuk debugging
                    }
                } else {
                    Toast.makeText(profileuser.this, "data "+ userId +"Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profileuser.this, "Gagal membaca informasi: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteUserDataAndAccount() {
        if (currentUser == null) {
            Toast.makeText(this, "Tidak ada pengguna yang login untuk dihapus.", Toast.LENGTH_SHORT).show();
            return;
        }

        final String email = currentUser.getEmail();
        if (email == null) {
            Toast.makeText(this, "Email pengguna tidak tersedia.", Toast.LENGTH_SHORT).show();
            return;
        }

        final String userIdi = email.replace(".", "_");

        // --- Langkah 1: Hapus data dari Realtime Database ---
        database.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("ProfileUser", "User data removed successfully from Realtime Database.");
                    Toast.makeText(profileuser.this, "Data pengguna berhasil dihapus.", Toast.LENGTH_SHORT).show();

                    // --- Langkah 2: Hapus akun dari Firebase Authentication ---
                    // PENTING: Pengguna harus baru-baru ini login untuk ini
                    currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("ProfileUser", "User account deleted successfully from Authentication.");
                                Toast.makeText(profileuser.this, "Akun berhasil dihapus.", Toast.LENGTH_SHORT).show();
                                // Setelah menghapus akun, arahkan pengguna ke layar awal/login
                                Intent intent = new Intent(profileuser.this, MainActivity.class); // Atau LoginActivity
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                // Tangani kegagalan penghapusan akun (misalnya, re-authentication diperlukan)
                                Log.e("ProfileUser", "Failed to delete user account: " + task.getException().getMessage());
                                Toast.makeText(profileuser.this, "Gagal menghapus akun: " + task.getException().getMessage() + ". Mohon Re-login Terlebih dahulu.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Log.e("ProfileUser", "Failed to remove user data from Realtime Database: " + task.getException().getMessage());
                    Toast.makeText(profileuser.this, "Gagal menghapus data pengguna: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
