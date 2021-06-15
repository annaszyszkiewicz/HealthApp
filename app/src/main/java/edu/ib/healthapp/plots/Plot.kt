package edu.ib.healthapp.plots

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color

import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import edu.ib.healthapp.plots.axis.Axis
import edu.ib.healthapp.plots.axis.AxisType
import edu.ib.healthapp.plots.axis.PlotLegend
import edu.ib.healthapp.plots.axis.properties.NumericAxisProperties
import edu.ib.healthapp.plots.axis.properties.StringAxisProperties
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

@SuppressLint("ViewConstructor")
class Plot<X,Y>: ViewGroup {

    private var seriesList: ArrayList<PlotSeries<X,Y>> = ArrayList();
    internal var plotCanvas: PlotCanvas<X,Y> = PlotCanvas(this,context)
    private lateinit var xAxis: Axis<X,Y>
    private lateinit var yAxis: Axis<X,Y>
    private val xClass: Class<X>
    private val yClass: Class<Y>
    private val textTitle: TextView
    private val legend: PlotLegend<X,Y> = PlotLegend(this,context)

    constructor(xClass: Class<X>,yClass: Class<Y>,context: Context?) : super(context){
        this.xClass=xClass
        this.yClass=yClass
        textTitle= TextView(context)
        init();
    }

    constructor(xClass: Class<X>,yClass: Class<Y>,context: Context?, attrs: AttributeSet): super(context,attrs){
        this.xClass=xClass
        this.yClass=yClass
        textTitle=TextView(context)
        init();
    }

    constructor(xClass: Class<X>,yClass: Class<Y>,context: Context?, attrs: AttributeSet,defStyleAttr :Int): super(context, attrs,defStyleAttr){
        this.xClass=xClass
        this.yClass=yClass
        textTitle=TextView(context)
        init();
    }

    constructor(xClass: Class<X>,yClass: Class<Y>,context: Context?, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs,defStyleAttr,defStyleRes){
        this.xClass=xClass
        this.yClass=yClass
        textTitle=TextView(context)
        init();
    }

    private fun init(){

        when(xClass.simpleName){
            "double","int","String"->{
                xAxis=Axis(xClass,yClass,this,AxisType.X,context)
            }

            else ->throw IllegalTypeException(xClass.simpleName)
        }
        when(yClass.simpleName){
            "double","int"->{
                yAxis=Axis(xClass,yClass,this,AxisType.Y,context)
            }
            else ->throw IllegalTypeException("")
        }
        textTitle.text="Title"

        addView(plotCanvas)
        addView(textTitle)
        addView(xAxis)
        addView(yAxis)
        addView(legend)

    }


    fun addSeries(series: PlotSeries<X,Y>){
        seriesList.add(series);
        update()
    }

    fun removeSeriesAt(index: Int){
        seriesList.removeAt(index)
        update()
    }

    fun removeSeries(plotSeries: PlotSeries<X,Y>){
        seriesList.remove(plotSeries)
        update()
    }

    fun clear(){
        seriesList.clear();
        update()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width=MeasureSpec.getSize(widthMeasureSpec)
        val height=MeasureSpec.getSize(heightMeasureSpec)
        if(legend.visibility == VISIBLE) {
            plotCanvas.x = 0.155f * width;
            plotCanvas.y = 0.1f * height;
            plotCanvas.measure(
                MeasureSpec.makeMeasureSpec(
                    (0.635 * width).toInt(),
                    MeasureSpec.EXACTLY
                ), MeasureSpec.makeMeasureSpec((0.735 * height).toInt(), MeasureSpec.EXACTLY)
            )


            xAxis.x = 0.15f * width
            xAxis.y = 0.830f * height
            xAxis.measure(
                MeasureSpec.makeMeasureSpec((0.64 * width).toInt(), MeasureSpec.EXACTLY),
                (MeasureSpec.makeMeasureSpec((0.16 * height).toInt(), MeasureSpec.EXACTLY))
            )

            yAxis.x = 0f
            yAxis.y = 0.1f * height

            yAxis.measure(
                MeasureSpec.makeMeasureSpec((0.16 * width).toInt(), MeasureSpec.EXACTLY),
                (MeasureSpec.makeMeasureSpec((0.74 * height).toInt(), MeasureSpec.EXACTLY))
            )

            textTitle.y = 0.02f * height
            textTitle.measure(
                MeasureSpec.makeMeasureSpec(
                    (0.5 * width).toInt(),
                    MeasureSpec.AT_MOST
                ), MeasureSpec.makeMeasureSpec((0.06 * height).toInt(), MeasureSpec.EXACTLY)
            )
            textTitle.x = 0.5f * width - textTitle.measuredWidth / 2f

            legend.x = 0.85f * width
            legend.y = 0.3f * height
            legend.measure(
                MeasureSpec.makeMeasureSpec((0.2 * width).toInt(), MeasureSpec.EXACTLY),
                (MeasureSpec.makeMeasureSpec((0.5 * height).toInt(), MeasureSpec.EXACTLY))
            )
        } else {
            plotCanvas.x = 0.155f * width;
            plotCanvas.y = 0.1f * height;
            plotCanvas.measure(
                MeasureSpec.makeMeasureSpec(
                    (0.785 * width).toInt(),
                    MeasureSpec.EXACTLY
                ), MeasureSpec.makeMeasureSpec((0.735 * height).toInt(), MeasureSpec.EXACTLY)
            )


            xAxis.x = 0.15f * width
            xAxis.y = 0.830f * height
            xAxis.measure(
                MeasureSpec.makeMeasureSpec((0.79 * width).toInt(), MeasureSpec.EXACTLY),
                (MeasureSpec.makeMeasureSpec((0.16 * height).toInt(), MeasureSpec.EXACTLY))
            )

            yAxis.x = 0f
            yAxis.y = 0.1f * height

            yAxis.measure(
                MeasureSpec.makeMeasureSpec((0.16 * width).toInt(), MeasureSpec.EXACTLY),
                (MeasureSpec.makeMeasureSpec((0.74 * height).toInt(), MeasureSpec.EXACTLY))
            )

            textTitle.y = 0.02f * height
            textTitle.measure(
                MeasureSpec.makeMeasureSpec(
                    (0.5 * width).toInt(),
                    MeasureSpec.AT_MOST
                ), MeasureSpec.makeMeasureSpec((0.06 * height).toInt(), MeasureSpec.EXACTLY)
            )
            textTitle.x = 0.5f * width - textTitle.measuredWidth / 2f

        }

        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count=childCount
        for(i in 0 until count){
            val child=getChildAt(i)
            child.layout(l,t,child.measuredWidth,child.measuredHeight)
        }
    }


    override fun shouldDelayChildPressedState(): Boolean {
        return false;
    }

    fun setLegendVisibility(visible: Boolean){
        if(visible) legend.visibility=VISIBLE;
        else legend.visibility= INVISIBLE;
    }


    private fun update(){

        if(xClass!=String::class.java) {

            var xMaxValue: Double;
            var xMinValue: Double;
            var yMaxValue: Double;
            var yMinValue: Double;
            if(seriesList.size==0) {
                xMaxValue = 10.0
                xMinValue = 0.0
                yMaxValue = 10.0
                yMinValue = 0.0

            } else {
                xMaxValue = Double.NEGATIVE_INFINITY
                xMinValue = Double.POSITIVE_INFINITY
                yMaxValue = Double.NEGATIVE_INFINITY
                yMinValue = Double.POSITIVE_INFINITY
                for (i in 0 until seriesList.size) {
                    val series = seriesList[i]
                    for (j in 0 until series.dataList.size) {
                        val data = series.getData(j)
                        xMaxValue= xMaxValue.coerceAtLeast(data.x as Double)
                        xMinValue= xMinValue.coerceAtMost(data.x as Double)
                        if(data.y!=null) {
                            yMaxValue = yMaxValue.coerceAtLeast(data.y as Double)
                            yMinValue = yMinValue.coerceAtMost(data.y as Double)
                        }
                    }
                }

            }
            if(yAxis.properties is NumericAxisProperties && (yAxis.properties as NumericAxisProperties).autoRanging) {
                (yAxis.properties as NumericAxisProperties).max = floor(yMaxValue + 1).toInt()
                (yAxis.properties as NumericAxisProperties).min = floor(yMinValue - 1).toInt()

                while (((yAxis.properties as NumericAxisProperties).max - (yAxis.properties as NumericAxisProperties).min) % yAxis.properties.labelCount != 0) {
                    (yAxis.properties as NumericAxisProperties).max++;
                }
                (yAxis.properties as NumericAxisProperties).step =
                    (((yAxis.properties as NumericAxisProperties).max - (yAxis.properties as NumericAxisProperties).min) / yAxis.properties.labelCount).toInt()
            }
            if(xAxis.properties is NumericAxisProperties && (xAxis.properties as NumericAxisProperties).autoRanging) {
                (xAxis.properties as NumericAxisProperties).max = floor(xMaxValue + 1).toInt()
                (xAxis.properties as NumericAxisProperties).min = floor(xMinValue - 1).toInt()

                while (((xAxis.properties as NumericAxisProperties).max - (xAxis.properties as NumericAxisProperties).min) % xAxis.properties.labelCount != 0) {
                    (xAxis.properties as NumericAxisProperties).max++;
                }

                (xAxis.properties as NumericAxisProperties).step =
                    (((xAxis.properties as NumericAxisProperties).max - (xAxis.properties as NumericAxisProperties).min) / xAxis.properties.labelCount).toInt()
            }


        } else {
            var maxValue=Double.NEGATIVE_INFINITY
            var minValue=Double.POSITIVE_INFINITY
            val fields=min(7,seriesList[0].dataList.size)
            for(i in 0 until seriesList.size){
                for(j in 0 until seriesList[i].dataList.size){
                    if(seriesList[i].dataList[j].y is Double){
                        if(seriesList[i].dataList[j].y!=null) {
                            maxValue = max(maxValue, seriesList[i].dataList[j].y as Double)
                            minValue = min(minValue, seriesList[i].dataList[j].y as Double)
                        }
                    } else {
                        if(seriesList[i].dataList[j].y!=null) {
                            maxValue =
                                max(maxValue, (seriesList[i].dataList[j].y as Int).toDouble())
                            minValue =
                                min(minValue, (seriesList[i].dataList[j].y as Int).toDouble())
                        }
                    }
                    if(seriesList[i].dataList[j] != seriesList[0].dataList[j]){
                        throw PlotException("Wrong data")
                    }
                }
            }

            var yMaxValue: Double;
            var yMinValue: Double;
            if(seriesList.size==0) {
                yMaxValue = 10.0
                yMinValue = 0.0
            } else {
                yMaxValue = Double.NEGATIVE_INFINITY
                yMinValue = Double.POSITIVE_INFINITY
                for (i in 0 until seriesList.size) {
                    val series = seriesList[i]
                    for (j in 0 until series.dataList.size) {
                        val data = series.getData(j)

                        if(data.y!=null) {
                            yMaxValue = yMaxValue.coerceAtLeast(data.y as Double)
                            yMinValue = yMinValue.coerceAtMost(data.y as Double)
                        }
                    }
                }

            }
            if(yAxis.properties is NumericAxisProperties && (yAxis.properties as NumericAxisProperties).autoRanging) {
                (yAxis.properties as NumericAxisProperties).max = floor(yMaxValue + 1).toInt()
                (yAxis.properties as NumericAxisProperties).min = floor(yMinValue - 1).toInt()

                while (((yAxis.properties as NumericAxisProperties).max - (yAxis.properties as NumericAxisProperties).min) % yAxis.properties.labelCount != 0) {
                    (yAxis.properties as NumericAxisProperties).max++;
                }
                (yAxis.properties as NumericAxisProperties).step =
                    (((yAxis.properties as NumericAxisProperties).max - (yAxis.properties as NumericAxisProperties).min) / yAxis.properties.labelCount).toInt()
            }

            val properties = xAxis.properties as StringAxisProperties

            properties.textFields=fields
        }
        postInvalidate()
    }


//getters and setters
    fun getXAxis(): Axis<X,Y>{
        return xAxis;
    }


    fun getYAxis(): Axis<X,Y>{
        return yAxis;
    }



    fun getSeriesList(): ArrayList<PlotSeries<X,Y>>{
        return seriesList;
    }


    fun getXClass(): Class<X>{
        return xClass;
    }

    fun getYClass(): Class<Y>{
        return yClass;
    }


    fun getTitle(): TextView{
        return textTitle;
    }





}




