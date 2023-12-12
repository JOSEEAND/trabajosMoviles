package com.demojosean.a14_firebaseaccess.entities

import java.sql.Timestamp

class cls_Orders {

    var OrderID: Int = 0
    var CustomerID: String=""
    var EmployeeID:Int=0
    lateinit var OrderDate: Timestamp
    lateinit var RequiredDate:Timestamp
    lateinit var ShippedDate: Timestamp
    var ShipVia:Int=0
    var Freight:Float=0F
    var ShipName:String=""
    var ShipAddress:String=""
    var ShipCity:String=""
    var ShipRegion:String=""
    var ShipPostalCode:String=""
    var ShipCountry:String=""

    constructor(){}
    constructor(
        OrderID:Int,CustomerID:String,EmployeeID:Int,OrderDate:Timestamp,
        RequiredDate:Timestamp,ShippedDate:Timestamp,ShipVia:Int,
        Freight:Float, ShipName:String,ShipAddress:String,ShipCity:String,
        ShipRegion:String,ShipPostalCode:String,ShipCountry:String
    ){
        this.OrderID=OrderID
        this.CustomerID=CustomerID
        this.EmployeeID=EmployeeID
        this.OrderDate=OrderDate
        this.RequiredDate=RequiredDate
        this.ShippedDate=ShippedDate
        this.ShipVia=ShipVia
        this.Freight=Freight
        this.ShipName=ShipName
        this.ShipAddress=ShipAddress
        this.ShipCity=ShipCity
        this.ShipRegion=ShipRegion
        this.ShipPostalCode=ShipPostalCode
        this.ShipCountry=ShipCountry
    }
}