package com.demojosean.a14_firebaseaccess

import androidx.appcompat.app.AppCompatActivity
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class ProductListActivity : AppCompatActivity() {

    var db = DBAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        // Creates local list view object
        val lstProducts = findViewById<ListView>(R.id.lstSimp)

        // Create an ArrayAdapter object to populate the list
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, android.R.id.text1, cargaTodosLosDatos()!!
        )

        // Assign adapter to the list view
        lstProducts.adapter = adapter

        // Define on click method over list items
        lstProducts.setOnItemClickListener { _, _, position, _ ->
            // Retrieves text from position
            val itemValue = lstProducts.getItemAtPosition(position) as String

            // Show Toast
            Toast.makeText(
                applicationContext,
                "Position: $position  Datos: $itemValue", Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun cargaTodosLosDatos(): Array<String?>? {
        // Open database
        db.open()

        // Retrieves all products with all columns
        val c: Cursor = db.getAllProducts

        // Array para almacenar todos los datos
        val todosLosDatos = arrayOfNulls<String>(c.count * c.columnCount)

        // Iterar a través de las filas del cursor
        var rowIndex = 0
        if (c.moveToFirst()) {
            do {
                // Iterar a través de las columnas de cada fila
                for (colIndex in 0 until c.columnCount) {
                    todosLosDatos[rowIndex++] = c.getString(colIndex)
                }
            } while (c.moveToNext())
        }

        // Close database
        db.close()

        // Return array con todos los datos
        return todosLosDatos
    }
}
