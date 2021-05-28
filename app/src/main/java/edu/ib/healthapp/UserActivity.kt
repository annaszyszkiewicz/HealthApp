package edu.ib.healthapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }

    fun onClickHistoryStats(view: View) {
        val intent = Intent(applicationContext, HistoryStatsActivity::class.java)
        startActivity(intent)
    }

    fun onClickReminders(view: View) {
        val intent = Intent(applicationContext, AllRemindersActivity::class.java)
        startActivity(intent)
    }

    fun onClickAddResults(view: View) {
        val intent = Intent(applicationContext, ResultsActivity::class.java)
        startActivity(intent)
    }


}