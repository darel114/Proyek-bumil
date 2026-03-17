package com.example.proyekbumil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvDaftarDisini = findViewById<TextView>(R.id.tvDaftarDisini)
        val btnMasuk = findViewById<Button>(R.id.btnMasuk)

        // Memberikan perintah klik untuk pindah ke halaman Register
        tvDaftarDisini.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Memberikan perintah klik untuk pindah ke halaman Dashboard
        btnMasuk.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}