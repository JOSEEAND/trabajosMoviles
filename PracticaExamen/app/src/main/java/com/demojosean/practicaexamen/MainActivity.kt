package com.demojosean.practicaexamen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var playerNameEditText: EditText
    private lateinit var betAmountEditText: EditText
    private lateinit var diceRadioButton1: RadioButton
    private lateinit var diceRadioButton2: RadioButton
    private lateinit var diceRadioButton3: RadioButton
    private lateinit var calendarView: CalendarView
    private lateinit var enterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa las vistas
        playerNameEditText = findViewById(R.id.editTextText)
        betAmountEditText = findViewById(R.id.editTextNumberDecimal)
        diceRadioButton1 = findViewById(R.id.radioButton)
        diceRadioButton2 = findViewById(R.id.radioButton2)
        diceRadioButton3 = findViewById(R.id.radioButton3)
        calendarView = findViewById(R.id.calendarView)
        enterButton = findViewById(R.id.button)

        // Puedes agregar acciones a realizar cuando se presione el botón "Ingresar"
        enterButton.setOnClickListener {
            val playerName = playerNameEditText.text.toString()
            val betAmount = betAmountEditText.text.toString()
            val selectedDiceCount = when {
                diceRadioButton1.isChecked -> 1
                diceRadioButton2.isChecked -> 2
                diceRadioButton3.isChecked -> 3
                else -> 0
            }
            val selectedDateMillis = calendarView.date

            // Crea un Intent para iniciar la MainActivity2
            val intent = Intent(this, MainActivity2::class.java)

            // Puedes pasar datos a la MainActivity2 si es necesario
            intent.putExtra("playerName", playerName)
            intent.putExtra("betAmount", betAmount)
            intent.putExtra("selectedDiceCount", selectedDiceCount)
            intent.putExtra("selectedDateMillis", selectedDateMillis)

            // Inicia la MainActivity2
            startActivity(intent)
        }
    }
}

