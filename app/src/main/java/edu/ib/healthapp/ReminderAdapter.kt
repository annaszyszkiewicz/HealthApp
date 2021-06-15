package edu.ib.healthapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import edu.ib.healthapp.activity.AddReminderActivity
import edu.ib.healthapp.activity.NewUserActivity
import java.util.*

class ReminderAdapter(val userID: Int, val db: SQLiteDatabase) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.reminder_item, parent, false)
        return ReminderViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        val cursor = db.query(
            TableInfo.TABLE_REMINDER, null,
            TableInfo.TABLE_REMINDER_COLUMN_USER + "=" + userID, null,
            null, null, null
        )
        val rows = cursor.count

        cursor.close()
        return rows
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val item = holder.view.findViewById<LinearLayout>(R.id.viewReminder)
        val info = holder.view.findViewById<TextView>(R.id.txtReminderInfo)
        val button = holder.view.findViewById<ImageButton>(R.id.ibtnDeleteReminder)
        val context: Context = holder.view.context

        val cursor = db.query(
            TableInfo.TABLE_REMINDER,
            arrayOf(
                TableInfo.TABLE_REMINDER_ID,
                TableInfo.TABLE_REMINDER_COLUMN_TYPE,
                TableInfo.TABLE_REMINDER_COLUMN_WORKER_MOST,
                TableInfo.TABLE_REMINDER_COLUMN_WORKER_LEAST,
                TableInfo.TABLE_REMINDER_COLUMN_DESCRIPTION,
            ),
            TableInfo.TABLE_REMINDER_COLUMN_USER + "=?", arrayOf(userID.toString()),
            null, null, null
        )

        cursor.moveToPosition(position)
        val text = "Przypomnienie o " + cursor.getString(1) + "\n" + cursor.getString(4)
        val uuid = UUID(cursor.getLong(2), cursor.getLong(3))
        val reminderId = cursor.getInt(0)
        cursor.close()

        if (cursor.moveToPosition(position)) {
            info.text = text
        }

        button.setOnClickListener {
            WorkManager.getInstance(context).cancelWorkById(uuid)
            db.delete(
                TableInfo.TABLE_REMINDER,
                TableInfo.TABLE_REMINDER_ID + "=?",
                arrayOf(reminderId.toString())
            )
            notifyDataSetChanged()
        }

    }

    class ReminderViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}