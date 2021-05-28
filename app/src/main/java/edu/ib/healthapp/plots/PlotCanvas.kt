package edu.ib.healthapp.plots

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import edu.ib.healthapp.plots.axis.properties.NumericAxisProperties

class PlotCanvas<X,Y>(var plot: Plot<X,Y>,context: Context?) : View(context) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLUE)

        val plotSeries=plot.getSeriesList();

        for(i in 0 until plotSeries.size){
            val series=plotSeries[i];
            for(j in 0 until series.dataList.size){

                if(plot.getXClass()!=String::class.java) {
                    val xAxisMax =(plot.getXAxis().getAxisProperties() as NumericAxisProperties).max;
                    val xAxisMin =(plot.getXAxis().getAxisProperties() as NumericAxisProperties).min;
                    val yAxisMax =(plot.getYAxis().getAxisProperties() as NumericAxisProperties).max;
                    val yAxisMin =(plot.getYAxis().getAxisProperties() as NumericAxisProperties).min;
                    Log.d("health","xAxisMax"+xAxisMax)
                    Log.d("health","xAxisMin"+xAxisMin)
                    Log.d("health","yAxisMax"+yAxisMax)
                    Log.d("health","yAxisMin"+yAxisMin)


                    val xRange=xAxisMax-xAxisMin;
                    val yRange=yAxisMax-yAxisMin;
                    val xCoordinate=(series.getData(j).x as Double-xAxisMin)/xRange * width;
                    val yCoordinate=height-(series.getData(j).y as Double-yAxisMin)/yRange * height;
                    when(series.type){
                        GraphType.XYChart ->{
                            canvas.drawCircle(xCoordinate.toFloat(),yCoordinate.toFloat(),width*0.005f,series.paint);
                        }
                        GraphType.Linear ->{
                            if(j!=0){
                                val lastXCoordinate=(series.getData(j-1).x as Double-xAxisMin)/xRange * width;
                                val lastYCoordinate=height-(series.getData(j-1).y as Double - yAxisMin)/yRange * height;
                                canvas.drawLine(lastXCoordinate.toFloat(),lastYCoordinate.toFloat(),xCoordinate.toFloat(),yCoordinate.toFloat(),series.paint);
                            }
                        }
                        GraphType.CategoryGraph ->{
                            canvas.drawRect(xCoordinate.toFloat()-0.005f*width,yCoordinate.toFloat(),xCoordinate.toFloat()+0.005f*width,height.toFloat(),series.paint);
                        }
                    }

                } else {
                    //tutaj dla String
                }

            }
        }

    }
}