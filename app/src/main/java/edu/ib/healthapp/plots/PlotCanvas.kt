package edu.ib.healthapp.plots

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import edu.ib.healthapp.plots.axis.properties.NumericAxisProperties
import edu.ib.healthapp.plots.axis.properties.StringAxisProperties

@SuppressLint("ViewConstructor")
class PlotCanvas<X, Y>(var plot: Plot<X, Y>, context: Context?) : View(context) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.LTGRAY)

        val plotSeries = plot.getSeriesList();
        if (plot.getXClass() != String::class.java) {
            for (i in 0 until plotSeries.size) {
                val series = plotSeries[i];
                for (j in 0 until series.dataList.size) {
                    if(series.getData(j).y==null) continue;

                    val xAxisMax =
                        (plot.getXAxis().getAxisProperties() as NumericAxisProperties).max
                    val xAxisMin =
                        (plot.getXAxis().getAxisProperties() as NumericAxisProperties).min
                    val yAxisMax =
                        (plot.getYAxis().getAxisProperties() as NumericAxisProperties).max
                    val yAxisMin =
                        (plot.getYAxis().getAxisProperties() as NumericAxisProperties).min

                    val xRange = xAxisMax - xAxisMin;
                    val yRange = yAxisMax - yAxisMin;
                    val xCoordinate = (series.getData(j).x as Double - xAxisMin) / xRange * width;
                    val yCoordinate =
                        height - (series.getData(j).y as Double - yAxisMin) / yRange * height;
                    when (series.type) {
                        GraphType.XYChart -> {
                            canvas.drawCircle(
                                xCoordinate.toFloat(),
                                yCoordinate.toFloat(),
                                width * 0.005f,
                                series.paint
                            );
                        }
                        GraphType.Linear -> {
                            if (j != 0) {
                                val lastXCoordinate =
                                    (series.getData(j - 1).x as Double - xAxisMin) / xRange * width;
                                val lastYCoordinate =
                                    height - (series.getData(j - 1).y as Double - yAxisMin) / yRange * height;
                                canvas.drawLine(
                                    lastXCoordinate.toFloat(),
                                    lastYCoordinate.toFloat(),
                                    xCoordinate.toFloat(),
                                    yCoordinate.toFloat(),
                                    series.paint
                                );
                            }
                        }
                        GraphType.CategoryGraph -> {
                            canvas.drawRect(
                                xCoordinate.toFloat() - 0.005f * width,
                                yCoordinate.toFloat(),
                                xCoordinate.toFloat() + 0.005f * width,
                                height.toFloat(),
                                series.paint
                            );
                        }

                    }
                }
            }
        } else {
            val seriesList = plot.getSeriesList()
            val dataCount = seriesList.size
            val propertiesY = plot.getYAxis().properties as NumericAxisProperties
            val max = propertiesY.max
            val min = propertiesY.min
            val range = max - min
            for (i in 0 until dataCount) {
                for (j in 0 until seriesList[i].dataList.size) {
                    if(seriesList[i].getData(j).y==null) continue;
                    val xCoordinate = width / (seriesList[i].dataList.size + 1) * (j + 1)
                    val yCoordinate =
                        height * ((seriesList[i].dataList[j].y as Double - min) / range)
                    when (seriesList[i].type) {
                        GraphType.XYChart -> {
                            canvas.drawCircle(
                                xCoordinate.toFloat(),
                                yCoordinate.toFloat(),
                                width * 0.005f,
                                seriesList[i].paint
                            );
                        }
                        GraphType.Linear -> {
                            if (j != 0) {
                                val lastXCoordinate = width / (seriesList[i].dataList.size + 1) * (j);
                                var k=j
                                while(k>0 && seriesList[i].dataList[k - 1].y==null){
                                    k--;
                                }
                                if(k!=0) {
                                    val lastYCoordinate =
                                        height * ((seriesList[i].dataList[k - 1].y as Double - min) / range)
                                    canvas.drawLine(
                                        lastXCoordinate.toFloat(),
                                        lastYCoordinate.toFloat(),
                                        xCoordinate.toFloat(),
                                        yCoordinate.toFloat(),
                                        seriesList[i].paint
                                    );
                                }
                            }
                        }
                        GraphType.CategoryGraph -> {
                            canvas.drawRect(
                                xCoordinate.toFloat() - 0.005f * width,
                                yCoordinate.toFloat(),
                                xCoordinate.toFloat() + 0.005f * width,
                                height.toFloat(),
                                seriesList[i].paint
                            );
                        }
                    }
                }
            }
        }

    }
}


