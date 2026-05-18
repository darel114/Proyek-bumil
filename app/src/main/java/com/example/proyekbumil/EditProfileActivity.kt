package com.example.proyekbumil

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etNama = findViewById<EditText>(R.id.etEditNama)
        val etEmail = findViewById<EditText>(R.id.etEditEmail)
        val etNIK = findViewById<EditText>(R.id.etEditNIK)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        etNama.setText(sharedPref.getString("nama", ""))
        etEmail.setText(sharedPref.getString("email", ""))
        etNIK.setText(sharedPref.getString("nik", ""))

        btnSimpan.setOnClickListener {
            sharedPref.edit().apply {
                putString("nama", etNama.text.toString())
                putString("email", etEmail.text.toString())
                putString("nik", etNIK.text.toString())
                apply()
            }
            finish()
        }
    }
}