package com.example.proyekbumil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        loadProfileData()
    }

    private fun loadProfileData() {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        findViewById<TextView>(R.id.tvProfileNama).text = sharedPref.getString("nama", "-")
        findViewById<TextView>(R.id.tvProfileEmail).text = sharedPref.getString("email", "-")
        findViewById<TextView>(R.id.tvProfileNIK).text = sharedPref.getString("nik", "-")
        findViewById<TextView>(R.id.tvProfileHPL).text = sharedPref.getString("hpl", "-")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // 1. Temukan Bottom Navigation di halaman Profil
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Set item "Profil" sebagai yang terpilih secara default
        bottomNavigationView.selectedItemId = R.id.menu_profile

        // Tambahkan perintah klik pada menu navigasi
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Kembali ke DashboardActivity
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish() // Tutup halaman profil agar pengguna kembali ke dashboard
                    true
                }
                R.id.menu_profile -> {
                    // Sudah berada di Profil, tidak perlu melakukan apa-apa
                    true
                }
                else -> false
            }
        }

        // 2. Logika untuk Tombol "Ubah Data"
        findViewById<Button>(R.id.btnUbahData).setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // 3. Logika untuk Tombol "Keluar Akun"
        val btnKeluarAkun = findViewById<Button>(R.id.btnKeluarAkun)
        btnKeluarAkun.setOnClickListener {
            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            // Hapus data session
            sharedPref.edit().clear().apply()

            // Buat intent untuk berpindah ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)

            // FLAG ini berguna untuk menghapus semua tumpukan halaman (history)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }
    }
}