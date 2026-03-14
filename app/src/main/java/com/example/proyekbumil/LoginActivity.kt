package com.example.proyekbumil

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // PENTING: Baris ini yang memanggil desain XML merah jambu Anda
        setContentView(R.layout.activity_login)

        // Mencari ID tombol "Daftar di sini"
        val tvDaftarDisini = findViewById<TextView>(R.id.tvDaftarDisini)

        // Memberikan perintah klik untuk pindah ke halaman Register
        tvDaftarDisini.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}