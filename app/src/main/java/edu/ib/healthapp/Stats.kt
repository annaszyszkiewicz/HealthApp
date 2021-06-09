package edu.ib.healthapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import edu.ib.healthapp.activity.PlotStatsActivity
import edu.ib.healthapp.plots.GraphType
import java.time.LocalDate

class Stats(val uId: Int,val db: SQLiteDatabase) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_stats, container, false)
        val button=view.findViewById<Button>(R.id.btnStats)

        val editText1=view.findViewById<EditText>(R.id.editTextDate1)
        val editText2=view.findViewById<EditText>(R.id.editTextDate2)

        val spinnerTime=view.findViewById<Spinner>(R.id.spTime)
        val text1=view.findViewById<TextView>(R.id.txtStartDate)
        val text2=view.findViewById<TextView>(R.id.txtEndDate)

        spinnerTime.onItemSelectedListener =
            object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem=parent?.selectedItem.toString()
                    when(selectedItem){
                        Type.Today.data,Type.ThisWeek.data, Type.ThisMonth.data, Type.LastWeek.data, Type.LastMonth.data->{
                            editText1.text.clear()
                            editText2.text.clear()
                            editText1.isEnabled = false
                            editText2.isEnabled = false
                            text1.text=""
                            text2.text=""
                        }
                        Type.DataBetween.data->{
                            editText1.text.clear()
                            editText2.text.clear()
                            editText1.isEnabled = true
                            editText1.inputType = InputType.TYPE_DATETIME_VARIATION_DATE
                            editText2.isEnabled = true
                            editText2.inputType = InputType.TYPE_DATETIME_VARIATION_DATE
                            text1.text="Data początkowa"
                            text2.text="Data końcowa"
                        }
                        Type.InsertMonth.data,Type.InsertWeek.data->{
                            editText1.text.clear()
                            editText2.text.clear()
                            editText1.isEnabled = true
                            editText2.isEnabled = true
                            editText1.inputType = InputType.TYPE_NUMBER_FLAG_SIGNED
                            editText2.inputType = InputType.TYPE_NUMBER_FLAG_SIGNED
                        }
                        Type.InsertMonth.data->{
                            text1.text="Wprowadź miesiąc"
                            text2.text="Wprowadź rok"
                        }
                        Type.InsertWeek.data->{
                            text1.text="Wprowadź tydzień"
                            text2.text="Wprowadź rok"
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        //series 1
        val spDatatype1=view.findViewById<Spinner>(R.id.spSeriesDatatype1)
        val spType1=view.findViewById<Spinner>(R.id.spSeriesType1)
        val spMultiData1=view.findViewById<Spinner>(R.id.spSeriesMultiData1)
        //series 2
        val spDatatype2=view.findViewById<Spinner>(R.id.spSeriesDatatype2)
        val spType2=view.findViewById<Spinner>(R.id.spSeriesType2)
        val spMultiData2=view.findViewById<Spinner>(R.id.spSeriesMultiData2)
        //series 3
        val spDatatype3=view.findViewById<Spinner>(R.id.spSeriesDatatype3)
        val spType3=view.findViewById<Spinner>(R.id.spSeriesType3)
        val spMultiData3=view.findViewById<Spinner>(R.id.spSeriesMultiData3)









        button.setOnClickListener{
            val intent= Intent(view.context, PlotStatsActivity::class.java);
            intent.putExtra("user_id",uId);
            val data:DataOptions?
            when (spinnerTime.selectedItem.toString()){
                Type.Today.data ->{
                    data = DataOptions(Type.Today)
                    data.startDate= LocalDate.now()
                    data.series1= GraphType.getGraphType(spType1.selectedItem.toString());
                    data.series1DataType= DataType.getDataType(spDatatype1.selectedItem.toString())
                    data.series1Option= SeriesOption.getSeriesOption(spMultiData1.selectedItem.toString())
                    data.series2= GraphType.getGraphType(spType2.selectedItem.toString());
                    data.series2DataType= DataType.getDataType(spDatatype2.selectedItem.toString())
                    data.series2Option= SeriesOption.getSeriesOption(spMultiData2.selectedItem.toString())
                    data.series3= GraphType.getGraphType(spType3.selectedItem.toString());
                    data.series3DataType= DataType.getDataType(spDatatype3.selectedItem.toString())
                    data.series3Option= SeriesOption.getSeriesOption(spMultiData3.selectedItem.toString())

                }
                Type.ThisWeek.data->{
                    data = DataOptions(Type.ThisWeek)
                    val todayDate=LocalDate.now()
                    val startDate=todayDate.minusDays(todayDate.dayOfWeek.value-1L)
                    val endDate=startDate.plusDays(7L);
                    data.startDate=startDate
                    data.endDate=endDate
                    data.series1= GraphType.getGraphType(spType1.selectedItem.toString());
                    data.series1DataType= DataType.getDataType(spDatatype1.selectedItem.toString())
                    data.series1Option= SeriesOption.getSeriesOption(spMultiData1.selectedItem.toString())
                    data.series2= GraphType.getGraphType(spType2.selectedItem.toString());
                    data.series2DataType= DataType.getDataType(spDatatype2.selectedItem.toString())
                    data.series2Option= SeriesOption.getSeriesOption(spMultiData2.selectedItem.toString())
                    data.series3= GraphType.getGraphType(spType3.selectedItem.toString());
                    data.series3DataType= DataType.getDataType(spDatatype3.selectedItem.toString())
                    data.series3Option= SeriesOption.getSeriesOption(spMultiData3.selectedItem.toString())
                }
                Type.LastWeek.data->{
                    data = DataOptions(Type.LastWeek)
                    val todayDate=LocalDate.now()
                    val startDate=todayDate.minusDays(todayDate.dayOfWeek.value-1L).minusDays(7L)
                    val endDate=startDate.plusWeeks(1L)
                    data.startDate=startDate
                    data.endDate=endDate
                    data.series1= GraphType.getGraphType(spType1.selectedItem.toString());
                    data.series1DataType= DataType.getDataType(spDatatype1.selectedItem.toString())
                    data.series1Option= SeriesOption.getSeriesOption(spMultiData1.selectedItem.toString())
                    data.series2= GraphType.getGraphType(spType2.selectedItem.toString());
                    data.series2DataType= DataType.getDataType(spDatatype2.selectedItem.toString())
                    data.series2Option= SeriesOption.getSeriesOption(spMultiData2.selectedItem.toString())
                    data.series3= GraphType.getGraphType(spType3.selectedItem.toString());
                    data.series3DataType= DataType.getDataType(spDatatype3.selectedItem.toString())
                    data.series3Option= SeriesOption.getSeriesOption(spMultiData3.selectedItem.toString())
                }
                Type.ThisMonth.data->{
                    data = DataOptions(Type.ThisMonth)
                    val todayDate=LocalDate.now()
                    val startDate=todayDate.minusDays(todayDate.dayOfMonth-1L)
                    val endDate=startDate.plusMonths(1L).minusDays(1L)
                    data.startDate=startDate
                    data.endDate=endDate
                    data.series1= GraphType.getGraphType(spType1.selectedItem.toString());
                    data.series1DataType= DataType.getDataType(spDatatype1.selectedItem.toString())
                    data.series1Option= SeriesOption.getSeriesOption(spMultiData1.selectedItem.toString())
                    data.series2= GraphType.getGraphType(spType2.selectedItem.toString());
                    data.series2DataType= DataType.getDataType(spDatatype2.selectedItem.toString())
                    data.series2Option= SeriesOption.getSeriesOption(spMultiData2.selectedItem.toString())
                    data.series3= GraphType.getGraphType(spType3.selectedItem.toString());
                    data.series3DataType= DataType.getDataType(spDatatype3.selectedItem.toString())
                    data.series3Option= SeriesOption.getSeriesOption(spMultiData3.selectedItem.toString())
                }
                Type.LastMonth.data->{
                    data = DataOptions(Type.LastMonth)
                    val todayDate=LocalDate.now()
                    val startDate=todayDate.minusDays(todayDate.dayOfMonth-1L).minusMonths(1L)
                    val endDate=startDate.plusMonths(1L).minusDays(1L)
                    data.startDate=startDate
                    data.endDate=endDate
                    data.series1= GraphType.getGraphType(spType1.selectedItem.toString());
                    data.series1DataType= DataType.getDataType(spDatatype1.selectedItem.toString())
                    data.series1Option= SeriesOption.getSeriesOption(spMultiData1.selectedItem.toString())
                    data.series2= GraphType.getGraphType(spType2.selectedItem.toString());
                    data.series2DataType= DataType.getDataType(spDatatype2.selectedItem.toString())
                    data.series2Option= SeriesOption.getSeriesOption(spMultiData2.selectedItem.toString())
                    data.series3= GraphType.getGraphType(spType3.selectedItem.toString());
                    data.series3DataType= DataType.getDataType(spDatatype3.selectedItem.toString())
                    data.series3Option= SeriesOption.getSeriesOption(spMultiData3.selectedItem.toString())
                }
                Type.InsertWeek.data->{
                    data = DataOptions(Type.InsertWeek)
                    val startDateOfYear=LocalDate.of(text1.text.toString().toInt(),1,1)
                    val startDate=startDateOfYear.minusDays(startDateOfYear.dayOfWeek.value.toLong()).plusWeeks(text2.text.toString().toLong())
                    val endDate=startDate.plusMonths(1L).minusDays(1L)
                    data.startDate=startDate
                    data.endDate=endDate
                    data.series1= GraphType.getGraphType(spType1.selectedItem.toString());
                    data.series1DataType= DataType.getDataType(spDatatype1.selectedItem.toString())
                    data.series1Option= SeriesOption.getSeriesOption(spMultiData1.selectedItem.toString())
                    data.series2= GraphType.getGraphType(spType2.selectedItem.toString());
                    data.series2DataType= DataType.getDataType(spDatatype2.selectedItem.toString())
                    data.series2Option= SeriesOption.getSeriesOption(spMultiData2.selectedItem.toString())
                    data.series3= GraphType.getGraphType(spType3.selectedItem.toString());
                    data.series3DataType= DataType.getDataType(spDatatype3.selectedItem.toString())
                    data.series3Option= SeriesOption.getSeriesOption(spMultiData3.selectedItem.toString())
                }
                Type.InsertMonth.data->{
                    data = DataOptions(Type.InsertMonth)
                }
                Type.DataBetween.data->{
                    data=DataOptions(Type.DataBetween)
                }
                else ->data=null;
            }
            intent.putExtra("plot_data",data)
            startActivity(intent);
        }
        return view
    }
}