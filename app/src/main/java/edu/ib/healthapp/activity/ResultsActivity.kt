package edu.ib.healthapp.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import edu.ib.healthapp.DataBaseHelper
import edu.ib.healthapp.TableInfo
import edu.ib.healthapp.databinding.ActivityResultsBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.editTxtResultDate.setText(LocalDate.now().toString())
        binding.editTxtResultTime.setText(
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString()
        )
        val intent = intent;
        if (intent.hasExtra("user_id")) userId = intent.getIntExtra("user_id", 0)

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
    }

    fun add(view: View) {
        val textField = binding.editTxtResult
        val dateField = binding.editTxtResultDate
        val timeField = binding.editTxtResultTime
        val spinner = binding.selectResultType
        val value = ContentValues();

        if (spinner.selectedItem == "ciśnienie krwi") {
            if (textField.text.toString().isNotEmpty() && dateField.text.toString()
                    .isNotEmpty() && timeField.text.toString().isNotEmpty()
            ) {
                value.put(
                    TableInfo.TABLE_RESULT_COLUMN_VALUE1,
                    textField.text.toString().split("/")[0]
                )
                value.put(
                    TableInfo.TABLE_RESULT_COLUMN_VALUE2,
                    textField.text.toString().split("/")[1]
                )
                value.put(
                    TableInfo.TABLE_RESULT_COLUMN_DATETIME,
                    LocalDateTime.of(
                        LocalDate.parse(dateField.text.toString()),
                        LocalTime.parse(timeField.text.toString())
                    ).toString()
                )
                value.put(
                    TableInfo.TABLE_RESULT_COLUMN_TYPE,
                    binding.selectResultType.selectedItem.toString()
                )
                value.put(TableInfo.TABLE_RESULT_COLUMN_USER, userId)
                val dataBaseHelper = DataBaseHelper(applicationContext)
                val db = dataBaseHelper.writableDatabase;
                db.insertOrThrow(TableInfo.TABLE_RESULT, null, value)
                Toast.makeText(applicationContext, "Zapisano wynik", Toast.LENGTH_SHORT).show()

                val intent = Intent(applicationContext, ResultInterpretationActivity::class.java)
                intent.putExtra("type", binding.selectResultType.selectedItem.toString())
                intent.putExtra("value", binding.editTxtResult.text.toString())
                intent.putExtra("user_id", userId)
                intent.putExtra("unit", binding.txtUnit.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext,"Niepoprawne dane!", Toast.LENGTH_LONG).show()
            }
        } else {
            if (textField.text.toString().isNotEmpty() && dateField.text.toString()
                    .isNotEmpty() && timeField.text.toString().isNotEmpty()
            ) {
                value.put(TableInfo.TABLE_RESULT_COLUMN_VALUE1, textField.text.toString())

                value.put(
                    TableInfo.TABLE_RESULT_COLUMN_DATETIME,
                    LocalDateTime.of(
                        LocalDate.parse(dateField.text.toString()),
                        LocalTime.parse(timeField.text.toString())
                    ).toString()
                )
                value.put(
                    TableInfo.TABLE_RESULT_COLUMN_TYPE,
                    binding.selectResultType.selectedItem.toString()
                )
                value.put(TableInfo.TABLE_RESULT_COLUMN_USER, userId)
                val dataBaseHelper = DataBaseHelper(applicationContext)
                val db = dataBaseHelper.writableDatabase;
                db.insertOrThrow(TableInfo.TABLE_RESULT, TableInfo.TABLE_RESULT_COLUMN_VALUE2, value)
                Toast.makeText(applicationContext, "Zapisano wynik", Toast.LENGTH_SHORT).show()

                val intent = Intent(applicationContext, ResultInterpretationActivity::class.java)
                intent.putExtra("type", binding.selectResultType.selectedItem.toString())
                intent.putExtra("value", binding.editTxtResult.text.toString())
                intent.putExtra("user_id", userId)
                intent.putExtra("unit", binding.txtUnit.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext,"Niepoprawne dane!", Toast.LENGTH_LONG).show()
            }
        }


    }
}