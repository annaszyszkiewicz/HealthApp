package edu.ib.healthapp

import edu.ib.healthapp.plots.GraphType
import java.io.Serializable
import java.time.LocalDate
import java.time.Month
import java.time.Year

class DataOptions(val type: Type):Serializable {

    var startDate: LocalDate? = null
    var endDate: LocalDate? = null
    var weekNumber: Int? = null
    var month: Month? = null
    var year: Year? = null
    var series1: GraphType? = null
    var series2: GraphType? = null
    var series3: GraphType? = null
    var series1Option: SeriesOption? = null
    var series2Option: SeriesOption? = null
    var series3Option: SeriesOption? = null
    var series1DataType: DataType = DataType.Off
    var series2DataType: DataType = DataType.Off
    var series3DataType: DataType = DataType.Off
    var legend: Boolean = true

}

enum class Type(val data:String){
    Today("Dzisiaj"),
    ThisWeek("Obecny tydzień"),
    LastWeek("Zeszły tydzień"),
    ThisMonth("Obecny miesiąc"),
    LastMonth("Zeszły miesiąc"),
    InsertMonth("Podaj miesiąc"),
    InsertWeek("Podaj tydzień"),
    DataBetween("Podaj zakres");
    companion object{
    fun returnType(string:String): Type? {
        return when (string) {
            Today.data -> {
                Today
            }
            ThisWeek.data -> {
                ThisWeek
            }
            LastWeek.data -> {
                LastWeek
            }
            ThisMonth.data -> {
                ThisMonth
            }
            LastMonth.data -> {
                LastMonth
            }
            InsertMonth.data -> {
                InsertMonth
            }
            InsertWeek.data -> {
                InsertWeek
            }
            DataBetween.data -> {
                DataBetween
            }
            else -> null
        }
    }
    }
}

enum class SeriesOption(val label:String){
    All("Wszystkie"),
    Average("Średnia"),
    Max("Max"),
    Min("Min");

    companion object{
        fun getSeriesOption(label:String): SeriesOption?{
            return when(label){
                All.label->All
                Average.label->Average
                Max.label->Max
                Min.label->Min
                else -> null
            }
        }
    }

}
enum class DataType(val label:String){
    Off("Wyłączone"),
    Weight("Waga"),
    PressureS("Ciśnienie krwi (sk)"),
    PressureD("Ciśnienie krwi (roz)"),
    Glucose("Poziom glukozy"),
    Temperature("Temperatura"),
    Water("Wypita woda");
    companion object{
        fun getDataType(label:String):DataType{
            return when(label){
                Off.label->Off
                Weight.label->Weight
                PressureS.label->PressureS
                PressureD.label->PressureD
                Glucose.label->Glucose
                Temperature.label->Temperature
                Water.label->Water
                else->Off
            }
        }
    }
}