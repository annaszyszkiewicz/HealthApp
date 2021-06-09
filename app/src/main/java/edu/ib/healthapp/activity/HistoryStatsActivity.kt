package edu.ib.healthapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import edu.ib.healthapp.DataBaseHelper
import edu.ib.healthapp.HistoryStatsAdapter
import edu.ib.healthapp.R

class HistoryStatsActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    var uId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_stats)
        val intent=intent;
        if(intent.hasExtra("user_id"))uId=intent.getIntExtra("user_id",0)
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        title = "HealthApp"
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("Historia wyników"))
        tabLayout.addTab(tabLayout.newTab().setText("Statystyki"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = HistoryStatsAdapter(this, supportFragmentManager,
            tabLayout.tabCount,uId,db)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}