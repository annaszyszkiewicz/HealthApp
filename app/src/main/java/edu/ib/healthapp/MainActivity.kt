package edu.ib.healthapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ib.healthapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //val usersList = generateDummyList(10)

        //binding.recyclerUsers.adapter = UserAdapter(usersList)
        //binding.recyclerUsers.adapter = UserAdapter(applicationContext)
        //binding.recyclerUsers.layoutManager = LinearLayoutManager(this)
        // binding.recyclerUsers.setHasFixedSize(true)


    }
//    private fun generateDummyList(size: Int): List<RecyclerItemActivit> {
//        val list = ArrayList<RecyclerItemActivit>()
//
//        for (i in 0 until size) {
//            val item = RecyclerItemActivit("Item $i")
//            list += item
//        }
//        return list
//    }
//

    fun onClickCreateUser(view: View) {
        val intent = Intent(applicationContext, NewUserActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        binding.recyclerUsers.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerUsers.adapter = UserAdapter(applicationContext, db)
    }
}