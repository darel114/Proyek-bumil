package com.example.proyekbumil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        updateNamaUser()
    }

    private fun updateNamaUser() {
        val tvDashboardNama = findViewById<TextView>(R.id.tvDashboardNama)
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val namaUser = sharedPref.getString("nama", "Bunda")
        tvDashboardNama.text = "Bunda $namaUser"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Temukan Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Set item "Beranda" sebagai yang terpilih secara default
        bottomNavigationView.selectedItemId = R.id.menu_home

        // Tambahkan perintah klik pada menu navigasi
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Sudah berada di Beranda, tidak perlu melakukan apa-apa
                    true
                }
                R.id.menu_profile -> {
                    // Buka ProfileActivity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true // Mengembalikan true untuk menandai item ini terpilih
                }
                else -> false
            }
        }

        // Logika Tandai Selesai
        val buttons = listOf(R.id.btn_k1, R.id.btn_k2, R.id.btn_k3, R.id.btn_k4, R.id.btn_k5, R.id.btn_k6)
        buttons.forEach { id ->
            findViewById<android.widget.Button>(id).setOnClickListener { btn ->
                btn.setBackgroundColor(android.graphics.Color.GRAY) // Contoh perubahan warna
                (btn as android.widget.Button).text = "Selesai"
            }
        }
    }
}