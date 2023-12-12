package com.demojosean.a14_firebaseaccess

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddProductActivity : AppCompatActivity() {

    lateinit var edtProductName: EditText
    lateinit var edtPrice: EditText
    lateinit var edtQuantity: EditText
    lateinit var edtDiscount: EditText
    lateinit var edtSubtotal: EditText
    lateinit var edtCode: EditText
    lateinit var btnSaveProduct: Button
    lateinit var dbAdapter: DBAdapter
    lateinit var btnCancel: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        edtCode = findViewById(R.id.edtCode)
        edtProductName = findViewById(R.id.edtProductName)
        edtPrice = findViewById(R.id.edtPrice)
        edtQuantity = findViewById(R.id.edtQuantity)
        edtDiscount = findViewById(R.id.edtDiscount)
        edtSubtotal = findViewById(R.id.edtSubtotal)
        btnSaveProduct = findViewById(R.id.btnSaveProduct)
        btnCancel = findViewById(R.id.btnCancel)

        dbAdapter = DBAdapter(this)

        btnSaveProduct.setOnClickListener {
            guardarProducto()
        }
        btnCancel.setOnClickListener {
            cancelarCompra()
        }
    }

    private fun cancelarCompra() {
        // Puedes realizar acciones adicionales si es necesario
        Toast.makeText(this, "Compra cancelada", Toast.LENGTH_SHORT).show()

        // Cierra la actividad actual
        finish()
    }


    private fun guardarProducto() {

        val code = edtCode.text.toString().toIntOrNull() ?: 0
        val productName = edtProductName.text.toString()
        val price = edtPrice.text.toString().toFloatOrNull() ?: 0.0f
        val quantity = edtQuantity.text.toString().toIntOrNull() ?: 0
        val discount = edtDiscount.text.toString().toFloatOrNull() ?: 0.0f
        val subtotal = edtSubtotal.text.toString().toFloatOrNull() ?: 0.0f

        // Validar que los campos no estén vacíos y que los datos sean válidos
        if (code > 0 && productName.isNotEmpty() && price >= 0 && quantity > 0 && discount >= 0 && subtotal >= 0) {
            val result = dbAdapter.insProduct(code, productName, price, quantity, discount, subtotal)

            if (result != -1L) {
                Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show()

                limpiarCampos()

                // Cerrar sesion o hacer otras cosas
                // finish()
            } else {
                Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Todos los campos deben estar llenos y los datos deben ser válidos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        edtCode.text.clear()
        edtProductName.text.clear()
        edtPrice.text.clear()
        edtQuantity.text.clear()
        edtDiscount.text.clear()
        edtSubtotal.text.clear()
    }
}


