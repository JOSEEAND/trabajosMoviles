package com.demojosean.a14_firebaseaccess.entities

class cls_OrderDetails {

    var OrderID:Int=0
    var ProductID:Int=0
    var UnitPrice:Float=0F
    var Quantity:Int=0
    var Discount:Float=0F

    constructor(){}
    constructor(
        OrderID:Int,ProductID:Int,UnitPrice:Float,
        Quantity:Int,Discount:Float
    ){
        this.OrderID=OrderID
        this.ProductID=ProductID
        this.UnitPrice=UnitPrice
        this.Quantity=Quantity
        this.Discount=Discount
    }
}