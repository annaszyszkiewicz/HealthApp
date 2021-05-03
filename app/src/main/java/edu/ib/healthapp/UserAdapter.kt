package edu.ib.healthapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


//class UserAdapter(private val usersList: List<RecyclerItemActivit>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
class UserAdapter(val context: Context, val db: SQLiteDatabase) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recycler_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        //usersList.size

        val cursor = db.query(
            TableInfo.TABLE_NAME, null,
            null, null,
            null, null, null
        )
        val rows = cursor.count

        cursor.close()
        return rows
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = holder.view.findViewById<CardView>(R.id.cardViewUser)
        val name = holder.view.findViewById<TextView>(R.id.txtUserName)
        val context: Context = holder.view.context

        val cursor = db.query(
            TableInfo.TABLE_NAME, null,
            BaseColumns._ID + "=?", arrayOf(holder.absoluteAdapterPosition.plus(1).toString()),
            null, null, null
        )


        if (cursor.moveToFirst()) {
            name.setText(cursor.getString(1) + " " + cursor.getString(2))
        }
        // val currentItem = usersList[position]
        // holder.nameView.text = currentItem.name

        item.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                val intent_edit = Intent(context, NewUserActivity::class.java)

                val name_edit = cursor.getString(1)
                val suranem_edit = cursor.getString(2)
                val birth_edit = cursor.getString(3)
                val height_edit = cursor.getString(4)
                val id_edit = holder.absoluteAdapterPosition.plus(1).toString()

                intent_edit.putExtra("name", name_edit)
                intent_edit.putExtra("surname", suranem_edit)
                intent_edit.putExtra("birth", birth_edit)
                intent_edit.putExtra("height", height_edit)
                intent_edit.putExtra("ID", id_edit)

                context.startActivity(intent_edit)
                return true
            }
        })
        cursor.close()
    }


    class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    // { val nameView: TextView = itemView.findViewById(R.id.txtUserName) }
}