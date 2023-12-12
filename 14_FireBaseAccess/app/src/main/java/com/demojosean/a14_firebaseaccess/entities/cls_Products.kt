package com.demojosean.a14_firebaseaccess.entities

class cls_Products {

    var ProductID: Int = 0
    var ProductName: String = ""
    var SupplierID: Int = 0
    var CategoryID: Int = 0
    var QuantityPerUnit: String = ""
    var UnitPrice: Float = 0F
    var UnitsInStock: Int = 0
    var UnitsOnOrder: Int = 0
    var ReorderLevel: Int = 0
    var Discontinued: Boolean = false

    constructor() {}

    constructor(ProductID: Int, ProductName: String, SupplierID: Int,
                CategoryID: Int,QuantityPerUnit: String, UnitPrice:Float,
                UnitsInStock:Int,UnitsOnOrder:Int,ReorderLevel:Int,
                Discontinued:Boolean) {
        this.ProductID=ProductID
        this.ProductName=ProductName
        this.SupplierID=SupplierID
        this.CategoryID = CategoryID
        this.QuantityPerUnit=QuantityPerUnit
        this.UnitPrice = UnitPrice
        this.UnitsInStock = UnitsInStock
        this.UnitsOnOrder = UnitsOnOrder
        this.ReorderLevel=ReorderLevel
        this.Discontinued=Discontinued
    }}