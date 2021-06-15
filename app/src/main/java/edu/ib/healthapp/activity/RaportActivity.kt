package edu.ib.healthapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.ib.healthapp.DataBaseHelper
import edu.ib.healthapp.TableInfo
import edu.ib.healthapp.databinding.ActivityRaportBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RaportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRaportBinding
    private var userId = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRaportBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        if (intent.hasExtra("user_id")) userId = intent.getIntExtra("user_id", 0)
    }

    fun generate(view: View) {

        try {
            val startDate = LocalDate.parse(binding.raportStartDate.text.toString())
            val endDate = LocalDate.parse(binding.raportEndDate.text.toString())

            val pressure = binding.checkPressure.isChecked
            val pulse = binding.checkPulse.isChecked
            val weight = binding.checkWeight.isChecked
            val temperature = binding.checkTemperature.isChecked
            val sugar = binding.checkGlucose.isChecked


            val stringBuilder = StringBuilder()

            val dbHelper = DataBaseHelper(applicationContext)
            val db = dbHelper.writableDatabase
            val userCursor = db.query(
                TableInfo.TABLE_USER,
                arrayOf(
                    TableInfo.TABLE_USER_COLUMN_NAME,
                    TableInfo.TABLE_USER_COLUMN_SURNAME
                ),
                TableInfo.TABLE_USER_ID + "=?", arrayOf(userId.toString()), null, null, null
            )
            userCursor.moveToNext()
            var filename =
                userCursor.getString(0) + userCursor.getString(1) + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy_MM_dd_mm_hh_ss")
                ) + ".csv"
            userCursor.close();
            val dataCursor = db.query(
                TableInfo.TABLE_RESULT,
                arrayOf(
                    TableInfo.TABLE_RESULT_COLUMN_DATETIME,
                    TableInfo.TABLE_RESULT_COLUMN_TYPE,
                    TableInfo.TABLE_RESULT_COLUMN_VALUE1,
                    TableInfo.TABLE_RESULT_COLUMN_VALUE2
                ),
                TableInfo.TABLE_RESULT_COLUMN_USER + "=? AND DATE(${TableInfo.TABLE_RESULT_COLUMN_DATETIME})>=DATE('${startDate.toString()}') AND DATE(${TableInfo.TABLE_RESULT_COLUMN_DATETIME})<=DATE('${endDate}')",
                arrayOf(userId.toString()),
                null,
                null,
                TableInfo.TABLE_RESULT_COLUMN_DATETIME
            )
            var index = 1;
            stringBuilder.append("Nr;Data;Typ;Wartość1;Wartość2").append("\n")
            while (dataCursor.moveToNext()) {
                when (dataCursor.getString(1)) {
                    "ciśnienie krwi" -> {
                        if (pressure) {
                            stringBuilder.append(index++).append(";")
                            stringBuilder.append(LocalDateTime.parse(dataCursor.getString(0)))
                                .append(";")
                            stringBuilder.append(dataCursor.getString(1)).append(";")
                            stringBuilder.append(dataCursor.getString(2)).append(";")
                            stringBuilder.append(dataCursor.getString(3)).append("\n")
                        }
                    }
                    "tętno" -> {
                        if (pulse) {
                            stringBuilder.append(index++).append(";")
                            stringBuilder.append(LocalDateTime.parse(dataCursor.getString(0)))
                                .append(";")
                            stringBuilder.append(dataCursor.getString(1)).append(";")
                            stringBuilder.append(dataCursor.getString(2)).append(";")
                            stringBuilder.append("null").append("\n")
                        }
                    }
                    "poziom glukozy" -> {
                        if (sugar) {
                            stringBuilder.append(index++).append(";")
                            stringBuilder.append(LocalDateTime.parse(dataCursor.getString(0)))
                                .append(";")
                            stringBuilder.append(dataCursor.getString(2)).append(";")
                            stringBuilder.append("null").append("\n")
                        }
                    }
                    "temperatura ciała" -> {
                        if (temperature) {
                            stringBuilder.append(index++).append(";")
                            stringBuilder.append(LocalDateTime.parse(dataCursor.getString(0)))
                                .append(";")
                            stringBuilder.append(dataCursor.getString(1)).append(";")
                            stringBuilder.append(dataCursor.getString(2)).append(";")
                            stringBuilder.append("null").append("\n")
                        }
                    }
                    "waga" -> {
                        if (weight) {
                            stringBuilder.append(index++).append(";")
                            stringBuilder.append(LocalDateTime.parse(dataCursor.getString(0)))
                                .append(";")
                            stringBuilder.append(dataCursor.getString(1)).append(";")
                            stringBuilder.append(dataCursor.getString(2)).append(";")
                            stringBuilder.append("null").append("\n")
                        }
                    }

                }
            }
            dataCursor.close()
            val finishedString = stringBuilder.toString()


            val file = File(getExternalFilesDir("raports"), filename)
            try {
                FileOutputStream(file).use { os ->
                    os.write(finishedString.toByteArray())
                    os.close()
                    Log.d("health", "file save success")
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Coś poszło nie tak!", Toast.LENGTH_LONG).show()
            }

            val intent = Intent(Intent.ACTION_SENDTO)
            val uri = Uri.fromFile(file)
            intent.type = "text/plain";
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(intent, "Wyślij Email"))
        } catch(e: Exception){
            Toast.makeText(this, "Coś poszło nie tak!", Toast.LENGTH_LONG).show()
        }
    }


}

