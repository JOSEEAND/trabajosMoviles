package com.demojosean.practicaexamen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Puedes realizar cualquier otra operación que desees en esta actividad
        // Para acceder a los datos pasados desde MainActivity, puedes hacerlo de la siguiente manera:

        val playerName = intent.getStringExtra("playerName")
        val betAmount = intent.getStringExtra("betAmount")
        val selectedDiceCount = intent.getIntExtra("selectedDiceCount", 0)
        val selectedDateMillis = intent.getLongExtra("selectedDateMillis", 0)

        // Haz lo que necesites con estos datos en esta actividad
    }
}
