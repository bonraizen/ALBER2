package com.example.alber2;

public class User_manage {
    private String nama, email, namaPerusahaan;

    private User_manage(){

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public User_manage(String nama, String email, String namaPerusahaan) {
        this.nama = nama;
        this.email = email;
        this.namaPerusahaan = namaPerusahaan;
    }


}