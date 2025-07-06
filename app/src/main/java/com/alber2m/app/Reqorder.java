package com.alber2m.app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Reqorder extends AppCompatActivity {

    private FirebaseUser currentUser;
    private DatabaseReference database,orderDatabase;

    Button btn_kirim;

    EditText etnama_lengkap,etnama_perusahaan,spesifikasi_alat,etemail,etnomor_telpon,ettanggal_sewa,etlama_sewa,etlokasi_proyek;
    Spinner spinalat_diperlukan;
    public void sumber(){
        btn_kirim = findViewById(R.id.btn_kirim);
        etnama_lengkap = findViewById(R.id.etnama_lengkap);
        etnama_perusahaan = findViewById(R.id.etnama_perusahaan);
        etemail = findViewById(R.id.etemail);
        etnomor_telpon = findViewById(R.id.etnomor_telpon);
        spesifikasi_alat = findViewById(R.id.spesifikasi_alat);
        ettanggal_sewa = findViewById(R.id.ettanggal_sewa);
        etlama_sewa = findViewById(R.id.etlama_sewa);
        etlokasi_proyek = findViewById(R.id.etlokasi_proyek);
        spinalat_diperlukan = findViewById(R.id.spinalat_diperlukan);
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to ettanggal_sewa
                        // MonthOfYear is 0-indexed, so add 1
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, (monthOfYear + 1), year);
                        ettanggal_sewa.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveOrderToFirebase() {
        // Ambil data dari EditText dan Spinner
        String namaLengkap = etnama_lengkap.getText().toString().trim();
        String namaPerusahaan = etnama_perusahaan.getText().toString().trim();
        String spesifikasiAlat = spesifikasi_alat.getText().toString().trim();
        String email = etemail.getText().toString().trim();
        String nomorTelepon = etnomor_telpon.getText().toString().trim();
        String tanggalSewa = ettanggal_sewa.getText().toString().trim();
        String lamaSewa = etlama_sewa.getText().toString().trim();
        String lokasiProyek = etlokasi_proyek.getText().toString().trim();
        String alatDibutuhkan = spinalat_diperlukan.getSelectedItem().toString(); // Ambil nilai dari Spinner

        orderDatabase = FirebaseDatabase.getInstance().getReference("order");

        // Validasi input (opsional, tapi sangat disarankan)
        if (namaLengkap.isEmpty() || namaPerusahaan.isEmpty() || spesifikasiAlat.isEmpty() ||
                email.isEmpty() || nomorTelepon.isEmpty() || tanggalSewa.isEmpty() ||
                lamaSewa.isEmpty() || lokasiProyek.isEmpty() || alatDibutuhkan.isEmpty()) {
            Toast.makeText(Reqorder.this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
            return; // Hentikan proses jika ada data kosong
        }

        // Buat objek HashMap untuk menyimpan data
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("namaLengkap", namaLengkap);
        orderData.put("namaPerusahaan", namaPerusahaan);
        orderData.put("spesifikasiAlat", spesifikasiAlat);
        orderData.put("email", email);
        orderData.put("nomorTelepon", nomorTelepon);
        orderData.put("tanggalSewa", tanggalSewa);
        orderData.put("lamaSewa", lamaSewa);
        orderData.put("lokasiProyek", lokasiProyek);
        orderData.put("alatDibutuhkan", alatDibutuhkan);
        orderData.put("status", "Menunggu Konfirmasi"); // Tambahkan status awal pesanan

        // Dapatkan ID unik untuk setiap pesanan baru
        String orderId = orderDatabase.push().getKey();
        String userid = currentUser.getEmail().replace(".","_");

        if (orderId != null) {
            orderDatabase.child(orderId).setValue(orderData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Reqorder.this, "Pesanan Sudah Diajukan !!!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Reqorder.this, "Gagal mengirim Pengajuan: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        } else {
            Toast.makeText(Reqorder.this, "Gagal membuat ID pesanan.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reqorder);

        sumber();
        getinfo();

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrderToFirebase();
            }
        });

        ettanggal_sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void getinfo(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        String email = currentUser.getEmail();
        String userId = email.replace(".", "_");
        database = FirebaseDatabase.getInstance().getReference("user").child(userId);
        if(currentUser != null) {
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    if (datasnapshot.exists()) {
                        //data dari database
                        String nama = datasnapshot.child("nama").getValue(String.class);
                        String perusahaan = datasnapshot.child("namaPerusahaan").getValue(String.class);
                        String email = datasnapshot.child("email").getValue(String.class);
                        String tlp = datasnapshot.child("nomorTelepon").getValue(String.class);

                        //implementasi database ke edittex
                        etnama_lengkap.setText(nama != null ? nama : " not defind" );
                        etnama_perusahaan.setText(perusahaan != null ? perusahaan : "not defind");
                        etemail.setText(email != null ? email : "not defind");
                        etnomor_telpon.setText(tlp != null ? tlp : "not define");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Reqorder.this, "Gagal membaca informasi: "+ userId + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}