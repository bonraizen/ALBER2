package com.alber2m.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class profileuser extends AppCompatActivity {

    private TextView Nama, Korporat, Email, Notlp, name_label;
    private Button btn_logout, btn_updateprofpeng, btn_Delakun;
    private DatabaseReference database,orderDatabase; // Reference for user data
    private FirebaseUser currentUser;
    private TableLayout table_history;


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
        table_history = findViewById(R.id.table_history);
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
        sumber(); // Initialize UI elements
        setinfoprofil();
        setinfoorder();


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
        AlertDialog.Builder builder = new AlertDialog.Builder(profileuser.this);
        builder.setTitle("Verifikasi Identitas");
        builder.setMessage("Untuk Menghapus Akun, harap masukkan kata sandi Anda saat ini.");

        final EditText input = new EditText(profileuser.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance()); // Ensure password dots
        LinearLayout layout = new LinearLayout(profileuser.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 0); // Add padding
        layout.addView(input);
        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String currentPassword = input.getText().toString();
            if (currentPassword.isEmpty()) {
                Toast.makeText(profileuser.this, "Kata sandi saat ini tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }


            // Reauthenticate user
            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);
            currentUser.reauthenticate(credential)
                    .addOnCompleteListener(reauthTask -> {
                        if (reauthTask.isSuccessful()) {
                            Log.d("profiluser", "Reauthentication successful.");
                        }
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
                    });
        });
        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    private void setinfoorder() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(profileuser.this, "Tidak ada pengguna yang login untuk melihat riwayat pesanan.", Toast.LENGTH_SHORT).show();
            return;
        }

        final String userEmail = currentUser.getEmail(); // Email pengguna saat ini

        orderDatabase = FirebaseDatabase.getInstance().getReference("order");

        // Hapus semua baris kecuali header
        int childCount = table_history.getChildCount();
        if (childCount > 1) { // pastikan ada lebih dari satu baris (baris header)
            table_history.removeViews(1, childCount - 1);
        }

        // Query data order berdasarkan email pengguna
        Query query = orderDatabase.orderByChild("email").equalTo(userEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Map<String, String>> orders = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Pastikan snapshot memiliki semua child yang diperlukan
                    String alatDibutuhkan = snapshot.child("alatDibutuhkan").getValue(String.class);
                    String tanggalSewa = snapshot.child("tanggalSewa").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);

                    if (alatDibutuhkan != null && tanggalSewa != null && status != null) {
                        Map<String, String> order = new HashMap<>();
                        order.put("jenisAlber", alatDibutuhkan);
                        order.put("tanggalPengajuan", tanggalSewa);
                        order.put("status", status);
                        orders.add(order);
                    }
                }

                // Urutkan pesanan berdasarkan tanggal pengajuan (tanggalSewa) dari yang paling lama ke yang paling baru
                // Format tanggal "dd/MM/yyyy"
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Collections.sort(orders, new Comparator<Map<String, String>>() {
                    @Override
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        try {
                            Date date1 = sdf.parse(o1.get("tanggalPengajuan"));
                            Date date2 = sdf.parse(o2.get("tanggalPengajuan"));
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return 0; // Jika parsing gagal, anggap sama
                        }
                    }
                });


                // Tambahkan baris data ke TableLayout
                int no = 1;
                for (Map<String, String> order : orders) {
                    TableRow row = new TableRow(profileuser.this);
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    // Kolom No
                    TextView tvNo = new TextView(profileuser.this);
                    tvNo.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvNo.setPadding(8, 8, 8, 8);
                    tvNo.setTextColor(getResources().getColor(R.color.black));
                    tvNo.setText(String.valueOf(no++));
                    tvNo.setGravity(Gravity.CENTER); // Tengah untuk No
                    row.addView(tvNo);

                    // Kolom Jenis ALBER
                    TextView tvJenisAlber = new TextView(profileuser.this);
                    tvJenisAlber.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
                    tvJenisAlber.setPadding(8, 8, 8, 8);
                    tvJenisAlber.setTextColor(getResources().getColor(R.color.black));
                    tvJenisAlber.setText(order.get("jenisAlber"));
                    tvJenisAlber.setGravity(Gravity.START); // Rata kiri untuk teks
                    row.addView(tvJenisAlber);

                    // Kolom Tanggal Pengajuan
                    TextView tvTanggalPengajuan = new TextView(profileuser.this);
                    tvTanggalPengajuan.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
                    tvTanggalPengajuan.setPadding(8, 8, 8, 8);
                    tvTanggalPengajuan.setTextColor(getResources().getColor(R.color.black));
                    tvTanggalPengajuan.setText(order.get("tanggalPengajuan"));
                    tvTanggalPengajuan.setGravity(Gravity.START); // Rata kiri untuk teks
                    row.addView(tvTanggalPengajuan);

                    // Kolom Status
                    TextView tvStatus = new TextView(profileuser.this);
                    tvStatus.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
                    tvStatus.setPadding(8, 8, 8, 8);
                    tvStatus.setTextColor(getResources().getColor(R.color.black));
                    tvStatus.setText(order.get("status"));
                    tvStatus.setGravity(Gravity.START); // Rata kiri untuk teks
                    row.addView(tvStatus);

                    table_history.addView(row);
                }

                if (orders.isEmpty()) {
                    Toast.makeText(profileuser.this, "Tidak ada riwayat pesanan.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(profileuser.this, "Gagal membaca riwayat pesanan: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
