package com.example.umrechnungapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.umrechnerapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {

    lateinit var inputArea: EditText
    lateinit var inputBirthdate: EditText
    lateinit var inputMoney: EditText
    lateinit var textResult: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputArea = findViewById(R.id.inputArea)
        inputBirthdate = findViewById(R.id.inputBirthdate)
        inputMoney = findViewById(R.id.inputMoney)
        textResult = findViewById(R.id.textResult)

        findViewById<Button>(R.id.btnConvertArea).setOnClickListener {
            convertArea()
        }

        findViewById<Button>(R.id.btnConvertAge).setOnClickListener {
            convertAge()
        }

        findViewById<Button>(R.id.btnConvertMoney).setOnClickListener {
            convertMoney()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun convertArea() {
        val area = inputArea.text.toString().toDoubleOrNull()
        if (area != null) {
            val fieldSize = 7140.0  // Standard-Fußballfeldgröße in m²
            val fields = area / fieldSize
            textResult.text = "Das entspricht ca. %.2f Fußballfeldern.".format(fields)
        } else {
            textResult.text = "Bitte gültige Fläche eingeben."
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertAge() {
        val input = inputBirthdate.text.toString()
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val birthDate = LocalDate.parse(input, formatter)
            val now = LocalDate.now()
            val days = ChronoUnit.DAYS.between(birthDate, now)
            val minutes = days * 24 * 60
            textResult.text = "Du bist ca. $minutes Minuten alt."
        } catch (e: Exception) {
            textResult.text = "Bitte Datum im Format JJJJ-MM-TT eingeben."
        }
    }

    @SuppressLint("SetTextI18n")
    private fun convertMoney() {
        val amount = inputMoney.text.toString().toLongOrNull()
        if (amount != null) {
            val seconds = amount // 1 Euro = 1 Sekunde als Vergleich
            val days = seconds / (60 * 60 * 24)
            val years = days / 365.0
            textResult.text = """
                $amount Sekunden entsprechen:
                → $days Tagen
                → %.1f Jahren
            """.trimIndent().format(years)
        } else {
            textResult.text = "Bitte gültigen Geldbetrag eingeben."
        }
    }
}
