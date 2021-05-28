package edu.ib.healthapp.plots.axis

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import edu.ib.healthapp.plots.Plot
import edu.ib.healthapp.plots.axis.properties.NumericAxisProperties


class PlotLegend<X,Y>: androidx.appcompat.widget.AppCompatTextView {

    private var plot: Plot<X,Y>;


    constructor(plot: Plot<X,Y>,context: Context) : super(context){
        this.plot=plot
    }
    constructor(plot: Plot<X,Y>,context: Context, attrs: AttributeSet?) : super(context, attrs){
        this.plot=plot
    }
    constructor(plot: Plot<X,Y>,context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        this.plot=plot
    }

    @Override
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)



        val seriesList=plot.getSeriesList();
        var out ="";

        for(i in 0 until seriesList.size){
            out+="   "+seriesList[i].title+"\n"
            canvas.drawCircle(0.01f*width,(i+0.8f)*(textSize+1.0f)+1.0f,0.01f*width,seriesList[i].paint)
        }
        text=out
        this.text

    }
}