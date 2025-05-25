package com.example.alber2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView Nama, Korporat, Email, Notlp;
    private Button back_button;
    private DatabaseReference database; // Reference for user data
    private FirebaseUser currentUser;

    // Method to initialize UI elements
    private void sumber() {
        Nama = findViewById(R.id.Nama);
        Korporat = findViewById(R.id.korporat);
        Email = findViewById(R.id.email);
        Notlp = findViewById(R.id.notlp);

        back_button = findViewById(R.id.back_button);
    }

    // Method to handle navigation back to beranda activity
    private void Move() {
        Intent move_back = new Intent(profileuser.this, beranda.class);
        startActivity(move_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profileuser);

        sumber(); // Initialize UI elements

        // Set click listener for the back button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Move(); // Navigate back
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // Get the UID of the currently logged-in user
            String userId = currentUser.getUid();

            // Get a reference to the 'users' node in the Firebase database for the specific user
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            database = firebaseDatabase.getReference("users").child(userId);

            // Add a ValueEventListener to read data from the database
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // DataSnapshot contains the data from the referenced database location
                    // Try to get the entire User_manage object directly from the dataSnapshot
                    User_manage userProfile = dataSnapshot.getValue(User_manage.class);

                    if (userProfile != null) {
                        // If the userProfile object is successfully retrieved, set the TextViews
                        Nama.setText(userProfile.getNama() != null ? userProfile.getNama() : "-");
                        Email.setText(userProfile.getEmail() != null ? userProfile.getEmail() : "-");
                        Korporat.setText(userProfile.getNamaPerusahaan() != null ? userProfile.getNamaPerusahaan() : "-");
                        // Convert Integer tlp to String before setting to TextView
                        Notlp.setText(userProfile.getTlp() != null ? String.valueOf(userProfile.getTlp()) : "-");
                    } else {
                        // If userProfile is null, it means no data or incorrect data structure
                        Log.d("profileuser", "User profile data is null or malformed for userId: " + userId);
                        Toast.makeText(profileuser.this, "Gagal memuat data profil. Data tidak ditemukan atau salah format.", Toast.LENGTH_LONG).show();
                        Nama.setText("-");
                        Email.setText("-");
                        Korporat.setText("-");
                        Notlp.setText("-");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // An error occurred while reading data
                    Log.w("profileuser", "Failed to read user data.", databaseError.toException());
                    Toast.makeText(profileuser.this, "Gagal memuat data pengguna: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            // If no user is logged in, redirect to the Login activity
            Log.d("profileuser", "No current user found. Redirecting to Login.");
            startActivity(new Intent(this, Login.class));
            finish(); // Close this activity
        }

        // Apply window insets for edge-to-edge display (from original code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
