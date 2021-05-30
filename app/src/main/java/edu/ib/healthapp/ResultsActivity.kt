package edu.ib.healthapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import edu.ib.healthapp.databinding.ActivityResultsBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    private var userId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.editTxtResultDate.setText(LocalDate.now().toString())
        binding.editTxtResultTime.setText(
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString()
        )
        val intent=intent;
        if(intent.hasExtra("user_id")) userId=intent.getIntExtra("user_id",0)

        binding.selectResultType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.txtUnit.text = ""
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    when (selectedItem) {
                        "tętno" -> binding.txtUnit.text = "/min"
                        "poziom glukozy" -> binding.txtUnit.text = "mg/dL"
                        "temperatura ciała" -> binding.txtUnit.text = "°C"
                        "waga" -> binding.txtUnit.text = "kg"
                        else -> binding.txtUnit.text = "mmHg"
                    }
                }

            }

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val createResultToast =
            Toast.makeText(applicationContext, "Zapisano wynik", Toast.LENGTH_SHORT)
/*
        binding.btnSaveResult.setOnClickListener {
            val result_value = binding.editTxtResult.text.toString()
            val result_date = binding.editTxtResultDate.text.toString()
            val result_time = binding.editTxtResultTime.text.toString()

            val value = ContentValues()

            value.put(TableInfo.TABLE_COLUMN_VALUE, result_value)
            value.put(TableInfo.TABLE_COLUMN_DATE, result_date)
            value.put(TableInfo.TABLE_COLUMN_TIME, result_time)

            if (result_value.isNotEmpty() && result_time.isNotEmpty() && result_date.isNotEmpty()) {
                db.insertOrThrow(TableInfo.TABLE_RESULT, null, value)
                createResultToast.show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Nie wprowadzono potrzebnych danych",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

 */
    }

    fun add(view: View) {
        val textField = binding.editTxtResult
        val dateField = binding.editTxtResultDate
        val timeField = binding.editTxtResultTime
        val value=ContentValues();

        if(textField.text.toString().isNotEmpty() && dateField.text.toString().isNotEmpty() && timeField.text.toString().isNotEmpty()) {
            value.put(TableInfo.TABLE_RESULT_COLUMN_VALUE, textField.text.toString())
            value.put(TableInfo.TABLE_RESULT_COLUMN_DATE, dateField.text.toString())
            value.put(TableInfo.TABLE_RESULT_COLUMN_TIME, timeField.text.toString())
            value.put(TableInfo.TABLE_RESULT_COLUMN_TYPE,binding.selectResultType.selectedItem.toString())
            value.put(TableInfo.TABLE_RESULT_COLUMN_USER,userId)
            val dataBaseHelper = DataBaseHelper(applicationContext)
            val db = dataBaseHelper.writableDatabase;
            db.insertOrThrow(TableInfo.TABLE_RESULT, null, value)
            Toast.makeText(applicationContext, "Zapisano wynik", Toast.LENGTH_SHORT).show()
        } else {

        }
    }

}