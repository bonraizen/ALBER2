package com.alber2m.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private DatabaseReference database;
    Button btnlupaPassword,btnsignup;
    ImageButton btnback;

    private TextView txtshow;

    private EditText email_input, password_input;
    String email, password;
    private Button signin;


    private void masukan() {
        btnsignup = findViewById(R.id.btnsignup);
        btnlupaPassword = findViewById(R.id.btnlupaPassword);
        btnback = findViewById(R.id.btnback);
        signin = findViewById(R.id.sign_in_button);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        txtshow = findViewById(R.id.txtshow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        masukan();

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sewa-alber-default-rtdb.firebaseio.com/");
        database = FirebaseDatabase.getInstance().getReference("user");


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsignup = new Intent(Login.this, SignUp.class);
                startActivity(pindahsignup);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahsignup = new Intent(Login.this, MainActivity.class);
                startActivity(pindahsignup);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnlupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lupa();
            }
        });

        txtshow.setOnClickListener(v -> {
            // Check if the password is currently visible
            boolean passwordVisible = (password_input.getTransformationMethod() == null);

            if (passwordVisible) {
                // If visible, hide the password
                password_input.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                // If hidden, show the password
                password_input.setTransformationMethod(null);
            }

            // Move the cursor to the end of the text to keep it visible after the change
            password_input.setSelection(password_input.getText().length());
        });
    }

    private void lupa(){
        // Get the email from the input field
        String userEmail = email_input.getText().toString().trim();

        // Check if the email field is empty
        if (userEmail.isEmpty()) {
            email_input.setError("masukan email untuk merubah kata sandi");
            email_input.requestFocus();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Password reset email sent to " + userEmail, Toast.LENGTH_LONG).show();
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(Login.this, "Failed to send reset email: " + errorMessage, Toast.LENGTH_LONG).show();
                        Log.e("Login", "Password reset failed for " + userEmail, task.getException());
                    }
                });
    }

    private void login() {
        email = email_input.getText().toString().trim();
        password = password_input.getText().toString().trim();

        if (email.isEmpty()) {
            email_input.setError("Email is required");
            email_input.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            password_input.setError("Password is required");
            password_input.requestFocus();
            return;
        }



        // Query the database for the entered email
//        database.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                        User user = userSnapshot.getValue(User.class);
//                        if (user != null && user.getPassword().equals(password)) {
//                            // Login successful, navigate to Beranda activit
//
//                            return;
//                        }
//                    }
//                    // Password doesn't match for the given email
//                    Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Email doesn't exist
//                    Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(Login.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    startActivity(new Intent(this,beranda.class));
                    finish();
                }else {
                        Exception e = task.getException();
                        Log.d("LoginActivity", "Email yang dicoba: " + email);
                        Log.d("LoginActivity", "Password yang dicoba: " + password);
                        if (e != null) {
                            Toast.makeText(this, "Login gagal" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("w", "error", e);
                        }
                        if (!task.isSuccessful()) {
                            try {
                                Thread.sleep(3000);
                                Toast.makeText(this, "Email atau Password Mungkain Salah "+"/tTunggu 3 detik", Toast.LENGTH_SHORT).show();
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                            btnlupaPassword.setVisibility(View.VISIBLE);
                        }else{
                            btnlupaPassword.setVisibility(View.VISIBLE);
                        }

                }
            });






    }
}