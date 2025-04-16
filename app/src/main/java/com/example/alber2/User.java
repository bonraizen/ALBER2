package com.example.alber2;

public class User {
    private String email;
    private String password;

    // Konstruktor kosong (diperlukan oleh Firebase untuk membaca data)
    public User() {
        // Diperlukan konstruktor kosong
    }

    // Konstruktor dengan parameter (opsional, tergantung kebutuhan)
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter untuk email
    public String getEmail() {
        return email;
    }

    // Setter untuk email (opsional)
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter untuk password
    public String getPassword() {
        return password;
    }

    // Setter untuk password (opsional)
    public void setPassword(String password) {
        this.password = password;
    }
}
