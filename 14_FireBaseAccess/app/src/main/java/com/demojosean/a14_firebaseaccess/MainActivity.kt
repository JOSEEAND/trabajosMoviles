package com.demojosean.a14_firebaseaccess

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.widget.Toast
import com.demojosean.a14_firebaseaccess.ui.users.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.ListView
import android.util.Log
import android.widget.Button
import com.demojosean.a14_firebaseaccess.entities.cls_Category
import com.demojosean.a14_firebaseaccess.entities.cls_Products
import com.demojosean.a14_firebaseaccess.entities.cls_Orders
import com.demojosean.a14_firebaseaccess.entities.cls_OrderDetails
import com.demojosean.a14_firebaseaccess.ui.categories.CategoryAdapter
import com.google.firebase.firestore.FirebaseFirestore

const val valorIntentLogin = 1

class MainActivity : AppCompatActivity() {

    var auth = FirebaseAuth.getInstance()
    var email: String? = null
    var contra: String? = null

    var db = FirebaseFirestore.getInstance()
    var TAG = "YorkTestingApp"

    lateinit var dbAdapter: DBAdapter
    lateinit var productListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddProduct: Button = findViewById(R.id.btnAddProduct)
        btnAddProduct.setOnClickListener {
            startActivity(Intent(this, AddProductActivity::class.java))
        }

        // intenta obtener el token del usuario del local storage, sino llama a la ventana de registro
        val prefe = getSharedPreferences("appData", Context.MODE_PRIVATE)
        email = prefe.getString("email", "")
        contra = prefe.getString("contra", "")

        if (email.toString().trim { it <= ' ' }.length == 0) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, valorIntentLogin)
        } else {
            val uid: String = auth.uid.toString()
            if (uid == "null") {
                auth.signInWithEmailAndPassword(email.toString(), contra.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Autenticación correcta", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
            // obtenerDatos()
            dbAdapter = DBAdapter(this)

            dbAdapter.open()

            productListView = findViewById(R.id.lstProducts)

            cargarDatosLocales()
            obtenerDatosFirestore()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbAdapter.close()
    }


    private fun cargarDatosLocales() {
        val cursor: Cursor = dbAdapter.getAllProducts
        val productos: ArrayList<cls_Category?> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                // Verifica si la columna existe antes de intentar obtener su índice
                val codigoIndex = cursor.getColumnIndex(DBAdapter.Codigo)
                val productoIndex = cursor.getColumnIndex(DBAdapter.Producto)
                val precioUnitarioIndex = cursor.getColumnIndex(DBAdapter.PrecioUnitario)
                val cantidadIndex = cursor.getColumnIndex(DBAdapter.Cantidad)
                val descuentoIndex = cursor.getColumnIndex(DBAdapter.Descuento)
                val subTotalIndex = cursor.getColumnIndex(DBAdapter.SubTotal)

                if (codigoIndex != -1 && productoIndex != -1 &&
                    precioUnitarioIndex != -1 && cantidadIndex != -1 &&
                    descuentoIndex != -1 && subTotalIndex != -1) {

                    // Accede a los datos solo si las columnas existen
                    val codigo = cursor.getInt(codigoIndex)
                    val producto = cursor.getString(productoIndex)
                    val precioUnitario = cursor.getFloat(precioUnitarioIndex)
                    val cantidad = cursor.getInt(cantidadIndex)
                    val descuento = cursor.getFloat(descuentoIndex)
                    val subTotal = cursor.getFloat(subTotalIndex)

                    val datos: cls_Category = cls_Category(codigo, producto, "", "")
                    productos.add(datos)
                }
            } while (cursor.moveToNext())

            // Cierra el cursor
            cursor.close()

            // Actualiza el ListView con los datos locales
            val adapter: CategoryAdapter = CategoryAdapter(this, productos)
            productListView.adapter = adapter
        }
    }


    private fun obtenerDatosFirestore() {
        // Realiza la consulta a Firestore y actualiza el ListView con los datos obtenidos
        db.collection("Products").orderBy("ProductID")
            .get()
            .addOnCompleteListener { docc ->
                if (docc.isSuccessful) {
                    for (document in docc.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        var datos: cls_Category = cls_Category(
                            document.data["ProductID"].toString().toInt(),
                            document.data["ProductName"].toString(),
                            document.data["UnitPrice"].toString(),
                            document.data["UnitsOnOrder"].toString()
                        )
                        /*Inserta el producto en la base de datos local utilizando DBAdapter
                        dbAdapter.insProduct(
                            datos.Codigo,
                            datos.Producto,
                            0.0,
                            0,
                            0.0,
                            0.0
                        )*/
                    }

                    cargarDatosLocales()

                    // Mueve el cierre de la base de datos aquí
                    dbAdapter.close()
                } else {
                    Log.w(TAG, "Error getting documents.", docc.exception)
                }
            }
    }


    /*private fun obtenerDatos() {
        //Toast.makeText(this,"Esperando hacer algo importante", Toast.LENGTH_LONG).show()
        var coleccion: ArrayList<cls_Category?> = ArrayList()
        var listaView: ListView = findViewById(R.id.lstCategories)
        db.collection("Categories").orderBy("CategoryID")
            .get()
            .addOnCompleteListener { docc ->
                if (docc.isSuccessful) {
                    for (document in docc.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                        var datos: cls_Category = cls_Category(document.data["CategoryID"].toString().toInt(),
                            document.data["CategoryName"].toString(),
                            document.data["Description"].toString(),
                            document.data["urlImage"].toString())
                        coleccion.add(datos)
                    }
                    var adapter: CategoryAdapter = CategoryAdapter(this, coleccion)
                    listaView.adapter =adapter
                } else {
                    Log.w(TAG, "Error getting documents.", docc.exception)
                }
            }
    }*/
}