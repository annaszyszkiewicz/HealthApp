package edu.ib.healthapp

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultAdapter(val userID: Int, val db: SQLiteDatabase) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.result_recycler, parent, false)
        return ResultViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        val cursor = db.query(TableInfo.TABLE_RESULT,null,
            TableInfo.TABLE_RESULT_COLUMN_USER+"="+userID, null,
            null, null, null
        )
        val rows = cursor.count

        cursor.close()
        return rows
    }



    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val id = holder.view.findViewById<TextView>(R.id.txtId)
        val date = holder.view.findViewById<TextView>(R.id.txtDate)
        val time = holder.view.findViewById<TextView>(R.id.txtTime)
        val type = holder.view.findViewById<TextView>(R.id.txtType)
        val value = holder.view.findViewById<TextView>(R.id.txtHistoryValue)
        val button = holder.view.findViewById<ImageButton>(R.id.ibtnDeleteResult)

        //val context: Context = holder.view.context

        val cursor = db.query(
                TableInfo.TABLE_RESULT,
            arrayOf(
                TableInfo.TABLE_RESULT_ID,
                TableInfo.TABLE_RESULT_COLUMN_DATE,
                TableInfo.TABLE_RESULT_COLUMN_TIME,
                TableInfo.TABLE_RESULT_COLUMN_TYPE,
                TableInfo.TABLE_RESULT_COLUMN_VALUE
            ),
            TableInfo.TABLE_RESULT_COLUMN_USER + "=?", arrayOf(userID.toString()),
            null, null, null
        )

        if (cursor.moveToPosition(position)) {
            id.text=cursor.getString(0)
            date.text=cursor.getString(1)
            time.text=cursor.getString(2)
            type.text=cursor.getString(3)
            value.text=cursor.getString(4)
        }

        button.setOnClickListener {
            db.delete(TableInfo.TABLE_RESULT,TableInfo.TABLE_RESULT_ID+"=?",arrayOf(cursor.getString(0)))
            notifyItemRemoved(position)
        };

    }


    class ResultViewHolder(val view:View) : RecyclerView.ViewHolder(view)

}