package com.example.proyekbumil

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private var calculatedHpl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etNama = findViewById<EditText>(R.id.etEditNama)
        val etUmur = findViewById<EditText>(R.id.etEditUmur)
        val etTinggi = findViewById<EditText>(R.id.etEditTinggi)
        val etBerat = findViewById<EditText>(R.id.etEditBerat)
        val etHPHT = findViewById<EditText>(R.id.etEditHPHT)
        val tvHPL = findViewById<TextView>(R.id.tvEditHPL)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        etNama.setText(sharedPref.getString("nama", ""))
        etUmur.setText(sharedPref.getString("umur", ""))
        etTinggi.setText(sharedPref.getString("tinggi", ""))
        etBerat.setText(sharedPref.getString("berat", ""))
        etHPHT.setText(sharedPref.getString("hpht", ""))
        calculatedHpl = sharedPref.getString("hpl", "") ?: ""
        if (calculatedHpl.isNotEmpty()) {
            tvHPL.text = "Perkiraan Tanggal Lahir (HPL): $calculatedHpl"
        }

        // Tambah DatePicker untuk HPHT di bagian edit profil
        val calendar = Calendar.getInstance()
        etHPHT.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedCalendar = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val hphtDateStr = sdf.format(selectedCalendar.time)
                    etHPHT.setText(hphtDateStr)

                    // Hitung taksiran persalinan (HPL)
                    val hplCalendar = Calendar.getInstance().apply {
                        time = selectedCalendar.time
                        add(Calendar.DAY_OF_YEAR, 280)
                    }
                    calculatedHpl = sdf.format(hplCalendar.time)
                    tvHPL.text = "Perkiraan Tanggal Lahir (HPL): $calculatedHpl"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString()
            val umur = etUmur.text.toString()
            val tinggi = etTinggi.text.toString()
            val berat = etBerat.text.toString()
            val hpht = etHPHT.text.toString()

            if (nama.isNotBlank() && umur.isNotBlank() && tinggi.isNotBlank() && berat.isNotBlank() && hpht.isNotBlank()) {
                sharedPref.edit().apply {
                    putString("nama", nama)
                    putString("umur", umur)
                    putString("tinggi", tinggi)
                    putString("berat", berat)
                    putString("hpht", hpht)
                    putString("hpl", calculatedHpl)
                    apply()
                }
                finish()
            }
        }
    }
}