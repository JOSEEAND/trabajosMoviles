package com.demojosean.a14_firebaseaccess

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ProductListAdapter(
    val context: Activity,
    val codigos: Array<String?>,
    val productos: Array<String?>,
    val preciosUnitarios: Array<String?>,
    val cantidades: Array<String?>,
    val descuentos: Array<String?>,
    val subTotales: Array<String?>
) : ArrayAdapter<String?>(context, R.layout.multidata, codigos) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView: View = inflater.inflate(R.layout.multidata, null, true)

        val txtCodigo = rowView.findViewById<View>(R.id.txtCodigo) as TextView
        val txtProducto = rowView.findViewById<View>(R.id.txtProducto) as TextView
        val txtPrecioUnitario = rowView.findViewById<View>(R.id.txtPrecio) as TextView
        val txtCantidad = rowView.findViewById<View>(R.id.txtCantidad) as TextView
        val txtDescuento = rowView.findViewById<View>(R.id.txtDescuento) as TextView
        val txtSubTotal = rowView.findViewById<View>(R.id.txtSubTotal) as TextView

        txtCodigo.text = codigos[position]
        txtProducto.text = productos[position]
        txtPrecioUnitario.text = preciosUnitarios[position]
        txtCantidad.text = cantidades[position]
        txtDescuento.text = descuentos[position]
        txtSubTotal.text = subTotales[position]

        return rowView
    }
}
