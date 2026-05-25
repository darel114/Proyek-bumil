package com.example.proyekbumil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        loadProfileData()
    }

    private fun loadProfileData() {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        findViewById<TextView>(R.id.tvProfileNama).text = sharedPref.getString("nama", "-")
        findViewById<TextView>(R.id.tvProfileUmur).text = "${sharedPref.getString("umur", "-")} Tahun"
        findViewById<TextView>(R.id.tvProfileTinggi).text = "${sharedPref.getString("tinggi", "-")} cm"
        findViewById<TextView>(R.id.tvProfileBerat).text = "${sharedPref.getString("berat", "-")} kg"
        findViewById<TextView>(R.id.tvProfileHPHT).text = sharedPref.getString("hpht", "-")
        val hpl = sharedPref.getString("hpl", "-")
        findViewById<TextView>(R.id.tvProfileHPL).text = hpl

        // Hitung sisa minggu kehamilan untuk ditaruh di header bulat hijau
        val hphtStr = sharedPref.getString("hpht", "")
        if (!hphtStr.isNullOrEmpty()) {
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val hphtDate = sdf.parse(hphtStr)
                if (hphtDate != null) {
                    val diffInMillis = Date().time - hphtDate.time
                    val diffInDays = diffInMillis / (1000 * 60 * 60 * 24)
                    val diffInWeeks = diffInDays / 7
                    val currentWeek = diffInWeeks.coerceAtLeast(0)
                    
                    // Sisa minggu kehamilan (Normal kehamilan itu ~40 minggu)
                    val remainingWeeks = (40 - currentWeek).coerceAtLeast(0)
                    findViewById<TextView>(R.id.tvSisaMingguValue)?.text = remainingWeeks.toString()
                }
            } catch (e: Exception) {
                findViewById<TextView>(R.id.tvSisaMingguValue)?.text = "-"
            }
        } else {
            findViewById<TextView>(R.id.tvSisaMingguValue)?.text = "-"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.menu_profile

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.menu_info -> {
                    val intent = Intent(this, InfoActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.menu_profile -> {
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

        // 3. Logika untuk Tombol "Hapus Data"
        val btnHapusData = findViewById<Button>(R.id.btnKeluarAkun)
        btnHapusData.text = "Hapus Data"
        btnHapusData.setOnClickListener {
            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}