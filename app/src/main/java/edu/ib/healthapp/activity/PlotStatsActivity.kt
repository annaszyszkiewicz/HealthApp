package edu.ib.healthapp.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import edu.ib.healthapp.R
import edu.ib.healthapp.plots.GraphType
import edu.ib.healthapp.plots.Plot
import edu.ib.healthapp.plots.PlotSeries

class PlotStatsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plot_stats)

        val intent = intent
        val data = intent.getSerializableExtra("plot_data")

        val plot = Plot(Double::class.java,Double::class.java,this.applicationContext)
        plot.x=0f
        plot.y=0f
        this.addContentView(plot, ViewGroup.LayoutParams(this.window.attributes.width,window.attributes.height))
        val plotSeries=PlotSeries(Double::class.java,Double::class.java,"Test",GraphType.Linear,
            Color.RED)
        for(i in 1..10){
            plotSeries.add(i.toDouble(),2.0*i)
        }
        plot.addSeries(plotSeries)
    }
}