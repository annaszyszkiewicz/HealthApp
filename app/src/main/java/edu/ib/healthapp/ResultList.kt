package edu.ib.healthapp

import java.util.stream.Collectors
import kotlin.math.max

class ResultList {

    val list: ArrayList<Result> = ArrayList()


    fun getListOfType(type:String): List<Result> {
        return list.stream().filter{
            it.type == type
        }.collect(Collectors.toList())
    }
    fun getMaxValueForType(type:String): Double{
        val value=Double.NEGATIVE_INFINITY;
        list.stream().forEach {
            it.value1?.let { it1 -> max(value, it1) }
        }
        return value
    }

    fun add(result: Result){

    }
}