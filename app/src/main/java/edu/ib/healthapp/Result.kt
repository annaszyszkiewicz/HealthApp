package edu.ib.healthapp

import android.database.Cursor
import java.time.LocalDateTime

class Result {
    var dateTime: LocalDateTime
    var type: String
    var value1: Double? = null
    var value2: Double? = null

    constructor(dateTime: LocalDateTime, type: String, value1: Double, value2: Double){
        this.dateTime=dateTime
        this.type=type
        this.value1=value1
        this.value2=value2
    }

    constructor(dateTime: LocalDateTime, type: String, value1: Double){
        this.dateTime=dateTime
        this.type=type
        this.value1 = value1
    }
}