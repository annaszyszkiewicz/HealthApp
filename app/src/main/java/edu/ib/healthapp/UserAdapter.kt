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

class UserAdapter(val context: Context, val db: SQLiteDatabase) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recycler_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        val cursor = db.query(
            TableInfo.TABLE_USER, null,
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
            TableInfo.TABLE_USER, null,
            null,null,
            null, null, null
        )

        if (cursor.moveToPosition(position)) {
            val userName = cursor.getString(1) + " " + cursor.getString(2)
            name.text = userName
        }


        val name_edit = cursor.getString(1)
        val suranem_edit = cursor.getString(2)
        val birth_edit = cursor.getString(3)
        val height_edit = cursor.getString(4)
        val id_edit = cursor.getString(0)

        item.setOnLongClickListener {
            val intent = Intent(context, NewUserActivity::class.java)
            intent.putExtra("name", name_edit)
            intent.putExtra("surname", suranem_edit)
            intent.putExtra("birth", birth_edit)
            intent.putExtra("height", height_edit)
            intent.putExtra("ID", id_edit)

            context.startActivity(intent)
            true
        }

        item.setOnClickListener {
            val intent = Intent(context, UserActivity::class.java)

            intent.putExtra("user_id", Integer.parseInt(id_edit))

            context.startActivity(intent)
        }
        cursor.close()
    }


    class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}