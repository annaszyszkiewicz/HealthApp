package edu.ib.healthapp.activity

import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import edu.ib.healthapp.*
import edu.ib.healthapp.plots.GraphType
import edu.ib.healthapp.plots.Plot
import edu.ib.healthapp.plots.PlotData
import edu.ib.healthapp.plots.PlotSeries
import edu.ib.healthapp.plots.axis.properties.NumericAxisProperties
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class PlotStatsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot_stats)

        val intent = intent
        val data = intent.getSerializableExtra("plot_data") as DataOptions

        var seriesNumber = 0;
        if (data.series1DataType != DataType.Off) {
            seriesNumber++;
        }
        if (data.series2DataType != DataType.Off) {
            seriesNumber++;
        }
        if (data.series3DataType != DataType.Off) {
            seriesNumber++;
        }

        val dbHelper = DataBaseHelper(applicationContext);
        val db = dbHelper.writableDatabase
        val r = Random()
        when (data.type) {
            Type.Today -> {
                val plot = Plot(Double::class.java, Double::class.java, this.applicationContext)
                if (seriesNumber == 1) {
                    plot.setLegendVisibility(false)
                }
                val axisXProps = plot.getXAxis().properties as NumericAxisProperties
                val axisYProps = plot.getYAxis().properties as NumericAxisProperties
                axisXProps.autoRanging = false
                axisXProps.min = 0
                axisXProps.max = 24

                //series 1
                if (data.series1DataType != DataType.Off) {

                    val series1 = PlotSeries(
                        Double::class.java,
                        Double::class.java,
                        data.series1DataType.label,
                        data.series1!!,
                        Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256))
                    )
                    val cursor = db.query(
                        TableInfo.TABLE_RESULT,
                        arrayOf(
                            TableInfo.TABLE_RESULT_COLUMN_DATETIME,
                            TableInfo.TABLE_RESULT_COLUMN_VALUE1,
                            TableInfo.TABLE_RESULT_COLUMN_VALUE2,
                        ),
                        TableInfo.TABLE_RESULT_COLUMN_DATETIME + "=? AND " + TableInfo.TABLE_RESULT_COLUMN_TYPE + "=?",
                        arrayOf(data.startDate.toString(), data.series1DataType.toString()),
                        null,
                        null,
                        TableInfo.TABLE_RESULT_COLUMN_DATETIME + " ASC",
                        null
                    )
                    val number = if (data.series1DataType == DataType.PressureS) {
                        2;
                    } else {
                        1;
                    }
                    while (cursor.moveToNext()) {
                        series1.add(
                            LocalTime.parse(cursor.getString(0)).hour.toDouble() + LocalTime.parse(
                                cursor.getString(0)
                            ).minute / 60.0, cursor.getDouble(number)
                        )
                    }
                    cursor.close()

                    //series 2
                    if (data.series2DataType != DataType.Off) {

                        val series2 = PlotSeries(
                            Double::class.java,
                            Double::class.java,
                            data.series2DataType.label,
                            data.series2!!,
                            Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256))
                        )
                        val cursor = db.query(
                            TableInfo.TABLE_RESULT,
                            arrayOf(
                                TableInfo.TABLE_RESULT_COLUMN_DATETIME,
                                TableInfo.TABLE_RESULT_COLUMN_VALUE1,
                                TableInfo.TABLE_RESULT_COLUMN_VALUE2,
                            ),
                            TableInfo.TABLE_RESULT_COLUMN_DATETIME + "=? AND " + TableInfo.TABLE_RESULT_COLUMN_TYPE + "=?",
                            arrayOf(data.startDate.toString(), data.series2DataType.toString()),
                            null,
                            null,
                            TableInfo.TABLE_RESULT_COLUMN_DATETIME + " ASC",
                            null
                        )
                        val number = if (data.series1DataType == DataType.PressureS) {
                            2;
                        } else {
                            1;
                        }
                        while (cursor.moveToNext()) {
                            series2.add(
                                LocalTime.parse(cursor.getString(0)).hour.toDouble() + LocalTime.parse(
                                    cursor.getString(0)
                                ).minute / 60.0, cursor.getDouble(number)
                            )
                        }
                        cursor.close()
                    }
                    //series 3
                    if (data.series3DataType != DataType.Off) {

                        val series3 = PlotSeries(
                            Double::class.java,
                            Double::class.java,
                            data.series3DataType.label,
                            data.series3!!,
                            Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256))
                        )
                        val cursor = db.query(
                            TableInfo.TABLE_RESULT,
                            arrayOf(
                                TableInfo.TABLE_RESULT_COLUMN_DATETIME,
                                TableInfo.TABLE_RESULT_COLUMN_VALUE1,
                                TableInfo.TABLE_RESULT_COLUMN_VALUE2,
                            ),
                            TableInfo.TABLE_RESULT_COLUMN_DATETIME + "=? AND " + TableInfo.TABLE_RESULT_COLUMN_TYPE + "=?",
                            arrayOf(data.startDate.toString(), data.series3DataType.toString()),
                            null,
                            null,
                            TableInfo.TABLE_RESULT_COLUMN_DATETIME + " ASC",
                            null
                        )
                        val number = if (data.series1DataType == DataType.PressureS) {
                            2;
                        } else {
                            1;
                        }
                        while (cursor.moveToNext()) {
                            val time=LocalTime.parse(cursor.getString(0))
                            series3.add(
                                time.hour.toDouble() + time.minute / 60.0, cursor.getDouble(number)
                            )
                        }
                        cursor.close()
                    }


                    val plot = Plot(Double::class.java, Double::class.java, this.applicationContext)
                    plot.x = 0f
                    plot.y = 0f
                    plot.addSeries(series1)
                    this.addContentView(
                        plot,
                        ViewGroup.LayoutParams(
                            this.window.attributes.width,
                            window.attributes.height
                        )
                    )
                }
            }

            else -> {

                //series 1
                val plotSeries1=PlotSeries(String::class.java,Double::class.java,data.series1DataType.label,data.series1!!,Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)))

                if(data.series1DataType!=DataType.Off) {
                    var statement = "SELECT DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+"), ";

                    when (data.series1Option) {
                        SeriesOption.Max -> {
                            statement += "MAX(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), MAX(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.Min -> {
                            statement += "MIN(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), MIN(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.Average -> {
                            statement += "AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.All -> {
                            statement += if (data.series1 != GraphType.CategoryGraph && data.series1 != GraphType.Linear) {
                                TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "," + TableInfo.TABLE_RESULT_COLUMN_VALUE2;
                            } else {
                                "AVG(+" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ")";
                            }
                        }
                    }
                    statement+=" FROM "+TableInfo.TABLE_RESULT+
                            " WHERE DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+")-DATE('${data.startDate.toString()}')>=0 AND DATE(${TableInfo.TABLE_RESULT_COLUMN_DATETIME})-DATE('${data.endDate.toString()}')<=0 GROUP BY DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+") "+" ORDER BY "+TableInfo.TABLE_RESULT_COLUMN_DATETIME

                    val cursor=db.rawQuery(statement,arrayOf())
                    val number=if(data.series1DataType==DataType.PressureD){
                        2
                    } else {
                        1
                    }
                    var dateIterator = data.startDate
                    while(cursor.moveToNext()) {

                        val date = LocalDate.parse(cursor.getString(0))
                        while(date.isEqual(dateIterator)){
                            plotSeries1.add(date.toString(),null)
                            dateIterator!!.plusDays(1L)
                        }
                        plotSeries1.add(date.toString(), cursor.getDouble(number))
                    }
                    cursor.close()

                }



                //series 2
                val plotSeries2=PlotSeries(String::class.java,Double::class.java,data.series2DataType.label,data.series2!!,Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)))

                if(data.series2DataType!=DataType.Off) {
                    var statement = "SELECT DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+"), ";

                    when (data.series2Option) {
                        SeriesOption.Max -> {
                            statement += "MAX(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), MAX(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.Min -> {
                            statement += "MIN(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), MIN(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.Average -> {
                            statement += "AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.All -> {
                            statement += if (data.series2 != GraphType.CategoryGraph && data.series2 != GraphType.Linear) {
                                TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "," + TableInfo.TABLE_RESULT_COLUMN_VALUE2;
                            } else {
                                "AVG(+" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ")";
                            }
                        }
                    }
                    statement+=" FROM "+TableInfo.TABLE_RESULT+
                            " WHERE DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+")-DATE('${data.startDate.toString()}')>=0 AND DATE(${TableInfo.TABLE_RESULT_COLUMN_DATETIME})-DATE('${data.endDate.toString()}')<=0 GROUP BY DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+") "+" ORDER BY "+TableInfo.TABLE_RESULT_COLUMN_DATETIME

                    val cursor=db.rawQuery(statement,arrayOf())
                    val number=if(data.series2DataType==DataType.PressureD){
                        2
                    } else {
                        1
                    }
                    var dateIterator = data.startDate
                    while(cursor.moveToNext()) {

                        val date = LocalDate.parse(cursor.getString(0))
                        while(date.isEqual(dateIterator)){
                            plotSeries2.add(date.toString(),null)
                            dateIterator!!.plusDays(1L)
                        }
                        plotSeries2.add(date.toString(), cursor.getDouble(number))
                    }
                    cursor.close()
                }

                //series 3
                val plotSeries3=PlotSeries(String::class.java,Double::class.java,data.series3DataType.label,data.series3!!,Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)))

                if(data.series3DataType!=DataType.Off) {
                    var statement = "SELECT DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+"), ";

                    when (data.series3Option) {
                        SeriesOption.Max -> {
                            statement += "MAX(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), MAX(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.Min -> {
                            statement += "MIN(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), MIN(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.Average -> {
                            statement += "AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ") "
                        }
                        SeriesOption.All -> {
                            statement += if (data.series3 != GraphType.CategoryGraph && data.series3 != GraphType.Linear) {
                                TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "," + TableInfo.TABLE_RESULT_COLUMN_VALUE2;
                            } else {
                                "AVG(+" + TableInfo.TABLE_RESULT_COLUMN_VALUE1 + "), AVG(" + TableInfo.TABLE_RESULT_COLUMN_VALUE2 + ")";
                            }
                        }
                    }
                    statement+=" FROM "+TableInfo.TABLE_RESULT+
                            " WHERE DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+")-DATE('${data.startDate.toString()}')>=0 AND DATE(${TableInfo.TABLE_RESULT_COLUMN_DATETIME})-DATE('${data.endDate.toString()}')<=0 GROUP BY DATE("+TableInfo.TABLE_RESULT_COLUMN_DATETIME+") "+" ORDER BY "+TableInfo.TABLE_RESULT_COLUMN_DATETIME

                    val cursor=db.rawQuery(statement,arrayOf())
                    val number=if(data.series3DataType==DataType.PressureD){
                        2
                    } else {
                        1
                    }
                    var dateIterator = data.startDate
                    while(cursor.moveToNext()) {

                        val date = LocalDate.parse(cursor.getString(0))
                        while(date.isEqual(dateIterator)){
                            plotSeries3.add(date.toString(),null)
                            dateIterator=dateIterator!!.plusDays(1L)
                        }
                        plotSeries3.add(date.toString(), cursor.getDouble(number))
                    }
                    cursor.close()

                }




                val plot = Plot(String::class.java, Double::class.java, this.applicationContext)
                if (seriesNumber == 1) {
                    plot.setLegendVisibility(false)
                }
                plot.x = 0f
                plot.y = 0f
                (plot.getYAxis().properties as NumericAxisProperties).autoRanging=false
                (plot.getYAxis().properties as NumericAxisProperties).min=0;
                (plot.getYAxis().properties as NumericAxisProperties).max=200
                if(plotSeries1.dataList.size>0){
                    plot.addSeries(plotSeries1)
                }
                if(plotSeries2.dataList.size>0){
                    plot.addSeries(plotSeries1)
                }
                if(plotSeries3.dataList.size>0){
                    plot.addSeries(plotSeries1)
                }

                this.addContentView(
                    plot,
                    ViewGroup.LayoutParams(this.window.attributes.width, window.attributes.height)
                )
            }

        }


    }
}