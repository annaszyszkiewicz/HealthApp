package edu.ib.healthapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import edu.ib.healthapp.databinding.ActivityNewUserBinding

class NewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val createUserToast = Toast.makeText(applicationContext, "Utworzono użytkownika",Toast.LENGTH_SHORT)

        if(intent.hasExtra("name")) binding.editTxtName.setText(intent.getStringExtra("name"))
        if(intent.hasExtra("surname")) binding.editTxtSurname.setText(intent.getStringExtra("surname"))
        if(intent.hasExtra("birth")) binding.editTxtDateOfBirth.setText(intent.getStringExtra("birth"))
        if(intent.hasExtra("height")) binding.editTxtHeight.setText(intent.getStringExtra("height"))


        binding.btnCreateUser.setOnClickListener({
            val name = binding.editTxtName.text.toString()
            val surname = binding.editTxtSurname.text.toString()
            val birth = binding.editTxtDateOfBirth.text.toString()
            val height = binding.editTxtHeight.text.toString()

            val value = ContentValues()

            value.put(TableInfo.TABLE_COLUMN_NAME, name)
            value.put(TableInfo.TABLE_COLUMN_SURNAME, surname)
            value.put(TableInfo.TABLE_COLUMN_BIRTH, birth)
            value.put(TableInfo.TABLE_COLUMN_HEIGHT, height)

            if(intent.hasExtra("ID")){

                db.update(TableInfo.TABLE_NAME, value, BaseColumns._ID + "=?", arrayOf(intent.getStringExtra("ID")))
                Toast.makeText(
                    applicationContext,
                    "Zmiany zapisano",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                if (!name.isEmpty() || !surname.isEmpty()) {
                    db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                    createUserToast.show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Nie wprowadzono potrzebnych danych",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }



        })
    }
}