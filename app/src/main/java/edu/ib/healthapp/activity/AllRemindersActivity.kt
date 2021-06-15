package edu.ib.healthapp.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ib.healthapp.DataBaseHelper
import edu.ib.healthapp.R
import edu.ib.healthapp.ReminderAdapter
import edu.ib.healthapp.ResultAdapter
import edu.ib.healthapp.databinding.ActivityAllRemindersBinding
import edu.ib.healthapp.databinding.ActivityMainBinding

class AllRemindersActivity : AppCompatActivity() {

    private var userId=0
    private lateinit var binding: ActivityAllRemindersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllRemindersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent=intent;
        if(intent.hasExtra("user_id")) userId=intent.getIntExtra("user_id",0)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val recycler = binding.recyclerReminders
        recycler.setHasFixedSize(true)
        recycler.layoutManager=LinearLayoutManager(applicationContext)
        recycler.adapter=ReminderAdapter(userId,db)

    }

    fun onClickAddReminder(view: View) {
        val intent = Intent(applicationContext, AddReminderActivity::class.java)
        intent.putExtra("user_id", userId)
        startActivity(intent)
    }

}