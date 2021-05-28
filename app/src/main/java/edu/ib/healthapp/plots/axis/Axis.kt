package edu.ib.healthapp.plots.axis

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import edu.ib.healthapp.plots.Plot
import edu.ib.healthapp.plots.axis.properties.AxisProperties
import edu.ib.healthapp.plots.axis.properties.NumericAxisProperties
import kotlin.collections.ArrayList

@SuppressLint("ViewConstructor")
class Axis<X, Y> : ViewGroup {

    private var plot: Plot<X, Y>;
    private val axisType: AxisType;
    private val xClass: Class<X>
    private val yClass: Class<Y>

    private val titleTextView: TextView = TextView(context)
    private val axisTextViewList: ArrayList<TextView> = ArrayList()
    private val valuesToDrawLines: ArrayList<Float> = ArrayList()

    val properties: AxisProperties;

    private val paint: Paint=Paint();

    // constructors -------------------------------------------------------------------------------------
    constructor(xClass: Class<X>, yClass: Class<Y>, plot: Plot<X, Y>, axisType: AxisType, context: Context?) : super(context) {
        this.xClass = xClass
        this.yClass = yClass
        this.plot = plot
        this.axisType = axisType;

        if (xClass == String::class.java && axisType == AxisType.X || yClass == String::class.java && axisType == AxisType.Y) {
            properties = AxisProperties()
        } else {
            properties = NumericAxisProperties()
        }

        for(i in 0 until properties.labelCount){
            val t=TextView(context)
            axisTextViewList.add(t)
            addView(t)
        }
    }

    constructor(xClass: Class<X>, yClass: Class<Y>, plot: Plot<X, Y>, axisType: AxisType, context: Context?, attrs: AttributeSet) : super(context, attrs) {
        this.xClass = xClass
        this.yClass = yClass
        this.plot = plot
        this.axisType = axisType
        if (xClass == String::class.java && axisType == AxisType.X || yClass == String::class.java && axisType == AxisType.Y) {
            properties = AxisProperties()
        } else {
            properties = NumericAxisProperties()
        }

        for(i in 0 until properties.labelCount){
            val t=TextView(context)
            axisTextViewList.add(t)
            addView(t)
        }
    }

    constructor(xClass: Class<X>, yClass: Class<Y>, plot: Plot<X, Y>, axisType: AxisType, context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.xClass = xClass
        this.yClass = yClass
        this.plot = plot
        this.axisType = axisType
        if (xClass == String::class.java && axisType == AxisType.X || yClass == String::class.java && axisType == AxisType.Y) {
            properties = AxisProperties()
        } else {
            properties = NumericAxisProperties()
        }

        for(i in 0 until properties.labelCount){
            val t=TextView(context)
            axisTextViewList.add(t)
            addView(t)
        }
    }

    constructor(xClass: Class<X>, yClass: Class<Y>, plot: Plot<X, Y>, axisType: AxisType, context: Context?, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        this.xClass = xClass
        this.yClass = yClass

        this.plot = plot
        this.axisType = axisType
        if (xClass == String::class.java && axisType == AxisType.X || yClass == String::class.java && axisType == AxisType.Y) {
            properties = AxisProperties()
        } else {
            properties = NumericAxisProperties()
        }

        for(i in 0 until properties.labelCount){
            val t=TextView(context)
            axisTextViewList.add(t)
            addView(t)
        }
    }

    init{
        setWillNotDraw(false)
    }

// methods ------------------------------------------------------------------------------------------

    override fun onDraw(canvas: Canvas) {
        if (context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            paint.color = Color.WHITE
        } else {
            paint.color = Color.BLACK
        }

        if (axisType == AxisType.X) {
            //canvas.drawColor(Color.CYAN)
            if (xClass == String::class.java) {

            } else {
                val prop=properties as NumericAxisProperties
                val xLeft=prop.leftAxisMargin*width
                val y=prop.topAxisMargin*height
                canvas.drawLine(xLeft,y,width.toFloat(),y,paint)
                canvas.drawLine(width.toFloat(),y,width*(1-0.05f),0f,paint)
                canvas.drawLine(width.toFloat(),y,width*(1-0.05f),2*y,paint)
                for(item in valuesToDrawLines){
                    canvas.drawLine(item,0f,item,2f*properties.topAxisMargin*height,paint)
                }
            }

        } else {
            //canvas.drawColor(Color.BLUE)
            if (yClass == String::class.java) {

            } else {
                val prop=properties as NumericAxisProperties
                val x=(1-prop.rightAxisMargin)*width
                val yDown=(1-prop.bottomAxisMargin)*height
                canvas.drawLine(x,yDown,x,0f,paint)
                canvas.drawLine(x,0f,x-prop.rightAxisMargin*width,0.05f*height,paint)
                canvas.drawLine(x,0f,width.toFloat(),0.05f*height,paint)
                for(item in valuesToDrawLines){
                    canvas.drawLine(width.toFloat(),item,width-2*properties.rightAxisMargin*width,item,paint)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        valuesToDrawLines.clear()
        this.removeAllViews()
        axisTextViewList.clear()

        for(i in 0 until properties.labelCount){
            val t=TextView(context)
            axisTextViewList.add(t)
            addView(t)
        }
        if (axisType == AxisType.X) {
            if (xClass == String::class.java) {

            } else {
                val prop: NumericAxisProperties = properties as NumericAxisProperties
                val pixelStep=((1-prop.leftAxisMargin)*width)/((prop.max-prop.min)/prop.step)
                for ((i, item) in axisTextViewList.withIndex()) {
                    item.text= (properties.min+(i*prop.step)).toString()
                    item.measure(MeasureSpec.makeMeasureSpec(pixelStep.toInt(),MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec((0.2*width).toInt(),MeasureSpec.EXACTLY))
                    //item.textSize= 0.19f*width
                    item.x=(pixelStep*i+prop.leftAxisMargin*width).toFloat()
                    valuesToDrawLines.add(item.x)
                    item.y=0.45f*height

                }
            }

        } else {
            if (yClass == String::class.java) {

            } else {
                for (item in axisTextViewList) {
                    val prop: NumericAxisProperties = properties as NumericAxisProperties
                    val pixelStep=((1-prop.bottomAxisMargin)*height)/((prop.max-prop.min)/prop.step)
                    for ((i, item) in axisTextViewList.withIndex()) {
                        item.text= (properties.min+(i*prop.step)).toString()
                        item.measure(MeasureSpec.makeMeasureSpec(pixelStep.toInt(),MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec((0.2*height).toInt(),MeasureSpec.AT_MOST))
                        //item.textSize = 12f
                        item.x=(0.7f)*width
                        item.y=0.95f*height-(pixelStep*i+prop.bottomAxisMargin*height).toFloat()
                        valuesToDrawLines.add(height-(pixelStep*i+prop.bottomAxisMargin*height).toFloat())
                    }
                }
            }
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

// getters and setters -------------------------------------------------------------------------


    fun getAxisType(): AxisType {
        return axisType
    }


    fun getAxisProperties(): AxisProperties {
        return properties
    }

}


enum class AxisType {
    X,
    Y
}