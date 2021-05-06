package com.example.whocallsyoumost

class Contact {

    var name: String? = null
    var number: String? = null
    var callCount: String? = null
    var durationSum: String? = null
    var typeMade: String? = null
    var typeMissed: String? = null
    var typeReceived: String? = null

    constructor(name: String, number: String,callCount:String,durationSum:String,typeMade:String,typeMissed:String,typeReceived:String) {
        this.name = name
        this.number = number
        this.callCount = callCount
        this.durationSum = durationSum
        this.typeMade = typeMade
        this.typeMissed = typeMissed
        this.typeReceived = typeReceived
    }

}

class CallType {
    var made: Int? = null
    var missed: Int?= null
    var received: Int?= null

    constructor(made: Int,missed: Int,received:Int){
        this.made = made
        this.missed = missed
        this.received = received
    }
}