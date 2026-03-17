package com.example.proyekbumil

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterActivity : AppCompatActivity() {

    // Deklarasi variabel untuk elemen UI
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etNIK: EditText
    private lateinit var etUmur: EditText
    private lateinit var etTinggi: EditText
    private lateinit var etBeratBadan: EditText
    private lateinit var btnPilihHPHT: Button
    private lateinit var tvHasilHPHT: TextView
    private lateinit var btnRegister: Button

    // Variabel untuk menyimpan tanggal
    private var kalenderHPHT = Calendar.getInstance()
    private var tanggalHPL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Memanggil desain XML yang sudah kita buat
        setContentView(R.layout.activity_register)

        // 1. Menghubungkan variabel Kotlin dengan ID di XML
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etNIK = findViewById(R.id.etNIK)
        etUmur = findViewById(R.id.etUmur)
        etTinggi = findViewById(R.id.etTinggi)
        etBeratBadan = findViewById(R.id.etBeratBadan)
        btnPilihHPHT = findViewById(R.id.btnPilihHPHT)
        tvHasilHPHT = findViewById(R.id.tvHasilHPHT)
        btnRegister = findViewById(R.id.btnRegister)

        // 2. Aksi saat tombol Pilih HPHT diklik
        btnPilihHPHT.setOnClickListener {
            tampilkanDatePicker()
        }

        // 3. Aksi saat tombol Daftar Sekarang diklik
        btnRegister.setOnClickListener {
            validasiDanDaftar()
        }
    }

    private fun tampilkanDatePicker() {
        val tahun = kalenderHPHT.get(Calendar.YEAR)
        val bulan = kalenderHPHT.get(Calendar.MONTH)
        val hari = kalenderHPHT.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            // Set kalender dengan tanggal yang dipilih
            kalenderHPHT.set(year, month, dayOfMonth)

            // Format tanggal untuk ditampilkan (Misal: 14 Maret 2026)
            val formatTanggal = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
            val tanggalDipilih = formatTanggal.format(kalenderHPHT.time)

            // Hitung HPL otomatis berdasarkan HPHT
            hitungHPL()

            // Tampilkan hasilnya ke layar
            tvHasilHPHT.text = "HPHT: $tanggalDipilih\nPerkiraan Lahir (HPL): $tanggalHPL"

        }, tahun, bulan, hari)

        // Mencegah pengguna memilih tanggal di masa depan untuk HPHT
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun hitungHPL() {
        // Menggunakan Rumus Naegele (+280 Hari dari HPHT)
        val kalenderHPL = kalenderHPHT.clone() as Calendar
        kalenderHPL.add(Calendar.DAY_OF_MONTH, 280)

        val formatTanggal = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        tanggalHPL = formatTanggal.format(kalenderHPL.time)
    }

    private fun validasiDanDaftar() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val nik = etNIK.text.toString().trim()
        val umur = etUmur.text.toString().trim()
        val tinggi = etTinggi.text.toString().trim()
        val beratBadan = etBeratBadan.text.toString().trim()

        // Validasi: Cek apakah ada yang kosong
        if (email.isEmpty() || password.isEmpty() || nik.isEmpty() ||
            umur.isEmpty() || tinggi.isEmpty() || beratBadan.isEmpty()) {
            Toast.makeText(this, "Mohon lengkapi semua data diri Anda", Toast.LENGTH_SHORT).show()
            return
        }

        // Validasi: Cek panjang NIK
        if (nik.length < 16) {
            etNIK.error = "NIK harus berjumlah 16 digit"
            etNIK.requestFocus()
            return
        }

        // Validasi: Cek apakah HPHT sudah diisi
        if (tanggalHPL.isEmpty()) {
            Toast.makeText(this, "Silakan pilih tanggal HPHT terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        // Jika semua validasi lolos
        Toast.makeText(this, "Validasi Berhasil! HPL Anda: $tanggalHPL", Toast.LENGTH_LONG).show()

        // Pindah ke Dashboard
        val intent = android.content.Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Menutup halaman registrasi
    }
}