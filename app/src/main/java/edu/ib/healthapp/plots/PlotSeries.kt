package edu.ib.healthapp.plots

import android.graphics.Color
import android.graphics.Paint


class PlotSeries<X,Y>(val xClass: Class<X>, val yClass: Class<Y>,var title: String, var type: GraphType) {
    var color: Int = Color.GREEN;
    val paint: Paint = Paint();
    var dataList: ArrayList<PlotData<X,Y>> = ArrayList();



    constructor(xClass:Class<X>, yClass: Class<Y>,title: String, type: GraphType,color: Int): this(xClass, yClass,title, type) {
        this.color = color;
        paint.color=color;
    }


    fun add(x: X, y:Y){
        dataList.add(PlotData(x,y));
    }

    fun add(index: Int, x: X, y:Y){
        dataList.add(index, PlotData(x,y))
    }

    fun getData(index: Int): PlotData<X,Y>{
        return dataList[index]
    }

    fun clear(){
        dataList.clear()
    }

    fun removeAt(index: Int){
        dataList.removeAt(index)
    }
    fun remove(plotData: PlotData<X, Y>){
        dataList.remove(plotData)
    }

}

enum class GraphType {
    Linear,
    XYChart,
    CategoryGraph
}


