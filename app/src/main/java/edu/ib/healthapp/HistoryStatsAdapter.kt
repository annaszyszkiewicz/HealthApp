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
    val userId: Int,
    val db: SQLiteDatabase
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                History(userId,db)
            }
            1 -> {
                Stats(userId,db)
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}