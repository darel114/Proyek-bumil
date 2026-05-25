package com.example.proyekbumil

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyekbumil.ui.theme.ProyekBumilTheme
import java.text.SimpleDateFormat
import java.util.*

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyekBumilTheme {
                OnboardingScreen(onSave = { nama, umur, tinggi, berat, hpht, hpl ->
                    val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        putBoolean("is_registered", true)
                        putString("nama", nama)
                        putString("umur", umur)
                        putString("tinggi", tinggi)
                        putString("berat", berat)
                        putString("hpht", hpht)
                        putString("hpl", hpl)
                        apply()
                    }
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onSave: (String, String, String, String, String, String) -> Unit) {
    val context = LocalContext.current
    var nama by remember { mutableStateOf("") }
    var umur by remember { mutableStateOf("") }
    var tinggi by remember { mutableStateOf("") }
    var berat by remember { mutableStateOf("") }
    var hpht by remember { mutableStateOf("") }
    var hpl by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            hpht = sdf.format(selectedCalendar.time)

            // Hitung taksiran persalinan (HPL / Naegele's rule)
            // (Hari + 7, Bulan - 3, Tahun + 1) -> setara dengan tambah 280 hari
            val hplCalendar = Calendar.getInstance().apply {
                time = selectedCalendar.time
                add(Calendar.DAY_OF_YEAR, 280)
            }
            hpl = sdf.format(hplCalendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Data Ibu Hamil", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama Lengkap") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = umur,
            onValueChange = { umur = it },
            label = { Text("Umur (Tahun)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = tinggi,
            onValueChange = { tinggi = it },
            label = { Text("Tinggi Badan (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = berat,
            onValueChange = { berat = it },
            label = { Text("Berat Badan Sebelum Kehamilan (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = hpht,
            onValueChange = {},
            label = { Text("Hari Pertama Haid Terakhir (HPHT)") },
            readOnly = true,
            enabled = false,
            colors = TextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                disabledIndicatorColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
        )

        if (hpl.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Perkiraan Tanggal Lahir (HPL): $hpl",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (nama.isNotBlank() && umur.isNotBlank() && tinggi.isNotBlank() && berat.isNotBlank() && hpht.isNotBlank()) {
                    onSave(nama, umur, tinggi, berat, hpht, hpl)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan Data")
        }
    }
}
