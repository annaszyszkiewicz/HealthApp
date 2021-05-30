package edu.ib.healthapp

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ib.healthapp.databinding.FragmentHistoryBinding

class History(private val userId: Int, val db: SQLiteDatabase) : Fragment() {
    //private lateinit var binding: FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        //binding = FragmentHistoryBinding.inflate(layoutInflater)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerHistory)
        recycler.setHasFixedSize(true)
        recycler.layoutManager=LinearLayoutManager(view.context.applicationContext)
        recycler.adapter=ResultAdapter(userId,db)
        return view
    }
}