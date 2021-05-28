package edu.ib.healthapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import edu.ib.healthapp.databinding.ActivityMainBinding
import edu.ib.healthapp.databinding.ActivityResultsBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.editTxtResultDate.setText(LocalDate.now().toString())
        binding.editTxtResultTime.setText(
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString()
        )

        binding.selectResultType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.txtUnit.setText("")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    when (selectedItem) {
                        "tętno" -> binding.txtUnit.setText("/min")
                        "poziom glukozy" -> binding.txtUnit.setText("mg/dL")
                        "temperatura ciała" -> binding.txtUnit.setText("°C")
                        "waga" -> binding.txtUnit.setText("kg")
                        else -> binding.txtUnit.setText("mm Hg")
                    }
                }

            }

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val createResultToast =
            Toast.makeText(applicationContext, "Zapisano wynik", Toast.LENGTH_SHORT)

        binding.btnSaveResult.setOnClickListener({
            val result_value = binding.editTxtResult.text.toString()
            val result_date = binding.editTxtResultDate.text.toString()
            val result_time = binding.editTxtResultTime.text.toString()

            val value = ContentValues()

            value.put(TableInfo.TABLE_COLUMN_VALUE, result_value)
            value.put(TableInfo.TABLE_COLUMN_DATE, result_date)
            value.put(TableInfo.TABLE_COLUMN_TIME, result_time)

            if (!result_value.isEmpty() && !result_time.isEmpty() && !result_date.isEmpty()) {
                db.insertOrThrow(TableInfo.TABLE_RESULT, null, value)
                createResultToast.show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Nie wprowadzono potrzebnych danych",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


}