package com.demojosean.a14_firebaseaccess

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBAdapter(val context: Context) {
    // Creates datastore
    var dbHelper: DatabaseHelper
    var db: SQLiteDatabase? = null

    // Opens datastore
    @Throws(SQLException::class)
    fun open(): DBAdapter {
        db = dbHelper.writableDatabase
        return this
    }

    // Close the datastore
    fun close() {
        dbHelper.close()
    }

    // ------------------------------------------
    // Commons Productos table methods
    // ------------------------------------------
    // Insert new product
    fun insProduct(
        codigo: Int,
        producto: String?,
        precioUnitario: Float,
        cantidad: Int,
        descuento: Float,
        subTotal: Float
    ): Long {
        val initialValues = ContentValues()
        initialValues.put(Codigo, codigo)
        initialValues.put(Producto, producto)
        initialValues.put(PrecioUnitario, precioUnitario)
        initialValues.put(Cantidad, cantidad)
        initialValues.put(Descuento, descuento)
        initialValues.put(SubTotal, subTotal)
        return db!!.insert(DB_Tabla_Productos, null, initialValues)
    }

    // Update a product
    fun updProduct(
        codigo: Int,
        producto: String?,
        precioUnitario: Float,
        cantidad: Int,
        descuento: Float,
        subTotal: Float
    ): Boolean {
        val args = ContentValues()
        args.put(Producto, producto)
        args.put(PrecioUnitario, precioUnitario)
        args.put(Cantidad, cantidad)
        args.put(Descuento, descuento)
        args.put(SubTotal, subTotal)
        return db!!.update(
            DB_Tabla_Productos,
            args,
            "$Codigo=$codigo",
            null
        ) > 0
    }

    // Delete a product
    fun delProduct(codigo: Int): Boolean {
        return db!!.delete(
            DB_Tabla_Productos,
            "$Codigo=$codigo",
            null
        ) > 0
    }

    // Retrieves all products
    val getAllProducts: Cursor
        get() = db!!.query(
            DB_Tabla_Productos,
            arrayOf(
                Codigo,
                Producto,
                PrecioUnitario,
                Cantidad,
                Descuento,
                SubTotal
            ),
            null,
            null,
            null,
            null,
            null,
            null
        )

    // Retrieves a specific product by its code
    fun getProductByCode(codigo: Int): Cursor? {
        val mCursor = db!!.query(
            true,
            DB_Tabla_Productos,
            arrayOf(
                Codigo,
                Producto,
                PrecioUnitario,
                Cantidad,
                Descuento,
                SubTotal
            ),
            "$Codigo=$codigo",
            null,
            null,
            null,
            null,
            null
        )
        mCursor?.moveToFirst()
        return mCursor
    }

    // Inner class to create and update the datastore
    inner class DatabaseHelper(context: Context?) :
        SQLiteOpenHelper(
            context,
            DB_Nombre,
            null,
            DB_Version
        ) {
        override fun onCreate(db: SQLiteDatabase) {
            try {
                // Try to create the datastore
                db.execSQL(creaBaseProductos)
            } catch (e: SQLException) {
                // Print in logcat the error found
                Log.e(TAG, e.message ?: "")
            }
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
            // Print in logcat the warning about the datastore changes
            Log.w(
                TAG,
                "Actualizando base de datos de la versión " + oldVersion + " a la "
                        + newVersion + ", los datos antiguos serán eliminados"
            )

            // Drop productos table
            db.execSQL("DROP TABLE IF EXISTS productos")

            // Call the constructor method to recreate the datastore
            onCreate(db)
        }
    }

    companion object {
        // Registra el nombre de la aplicación para el log
        const val TAG = "Datos"

        // Datos generales del almacenamiento local
        const val DB_Nombre = "LocalDB"
        const val DB_Version = 1
        const val DB_Tabla_Productos = "productos"

        // Definición de los campos de la tabla productos
        const val Codigo = "codigo"
        const val Producto = "producto"
        const val PrecioUnitario = "precio_unitario"
        const val Cantidad = "cantidad"
        const val Descuento = "descuento"
        const val SubTotal = "sub_total"

        // Construye comando SQL para crear la tabla productos
        const val creaBaseProductos =
            "create table productos(" +
                    "codigo integer primary key autoincrement, " +
                    "producto text not null, " +
                    "precio_unitario real not null, " +
                    "cantidad integer not null, " +
                    "descuento real not null, " +
                    "sub_total real not null);"
    }

    // --------------------------------------
    // Commons database methods
    // --------------------------------------
    // Class constructor
    init {
        // Creates a new instance of the database helper
        dbHelper = DatabaseHelper(context)
    }

    @SuppressLint("Range")
    fun Cursor.getProductoArray(columnName: String): Array<String?> {
        val array = mutableListOf<String?>()
        moveToFirst()
        while (!isAfterLast) {
            array.add(getString(getColumnIndex(columnName)))
            moveToNext()
        }
        return array.toTypedArray()
    }
}
