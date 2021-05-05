package com.example.whocallsyoumost

class Contact {

    var name: String? = null
    var number: String? = null
    var callCount: String? = null
    var durationSum: String? = null

    constructor(name: String, number: String,callCount:String,durationSum:String) {
        this.name = name
        this.number = number
        this.callCount = callCount
        this.durationSum = durationSum
    }

}