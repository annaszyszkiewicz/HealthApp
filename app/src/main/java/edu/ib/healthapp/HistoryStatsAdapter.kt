package edu.ib.healthapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
internal class HistoryStatsAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    val uId: Int,
    val db: SQLiteDatabase
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                History(uId,db)
            }
            1 -> {
                Stats(uId,db)
            }
            else -> throw Exception("Exception")
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}