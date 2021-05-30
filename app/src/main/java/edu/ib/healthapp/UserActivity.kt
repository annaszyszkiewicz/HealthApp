package edu.ib.healthapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class UserActivity : AppCompatActivity() {

    private var userId = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val intent=intent;
        if(intent.hasExtra("user_id")) userId=intent.getIntExtra("user_id",0)
    }

    fun onClickHistoryStats(view: View) {
        val intent = Intent(applicationContext, HistoryStatsActivity::class.java)
        intent.putExtra("user_id",userId)
        startActivity(intent)
    }

    fun onClickReminders(view: View) {
        val intent = Intent(applicationContext, AllRemindersActivity::class.java)
        intent.putExtra("user_id",userId)
        startActivity(intent)
    }

    fun onClickAddResults(view: View) {
        val intent = Intent(applicationContext, ResultsActivity::class.java)
        intent.putExtra("user_id",userId)
        startActivity(intent)
    }


}