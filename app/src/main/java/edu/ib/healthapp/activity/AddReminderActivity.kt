package edu.ib.healthapp.activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import edu.ib.healthapp.DataBaseHelper
import edu.ib.healthapp.TableInfo
import edu.ib.healthapp.databinding.ActivityAddReminderBinding
import edu.ib.healthapp.worker.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class AddReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReminderBinding
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.editTextReminderTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString())
        binding.editTextReminderInterval.setText("1")

        val intent = intent;
        if (intent.hasExtra("user_id")) userId = intent.getIntExtra("user_id", 0)
    }

    fun onClickSaveReminder(view: View) {
        val reminderType = binding.spinnerReminderType
        val reminderTimeHour = binding.editTextReminderTime
        val reminderInterval = binding.editTextReminderInterval
        val reminderDesc = binding.editTextReminderInfo
        val value = ContentValues()

        //WorkManager.getInstance(this).cancelAllWork()
        if (reminderTimeHour.text.toString().isNotEmpty() && reminderInterval.text.toString()
                .isNotEmpty()
        ) {

            var reminderFrequency = when (binding.spinnerReminderInterval.selectedItem) {
                "godzinę/godziny" -> 60L
                "dzień/dni" -> 1440L
                "tydzień/tygodnie" -> 10080L
                else -> throw Exception("")
            }

            reminderFrequency *= reminderInterval.text.toString().toLong()
            reminderFrequency += 3L

            val selectedReminderType = binding.spinnerReminderType.selectedItem
            val dailyWorkRequest: PeriodicWorkRequest.Builder
            when (selectedReminderType) {
                "pomiarze ciśnienia" -> dailyWorkRequest = PeriodicWorkRequest.Builder(
                    NotificationWorkerPressureMeasurement::class.java,
                    reminderFrequency,
                    TimeUnit.MINUTES,
                    6L,
                    TimeUnit.MINUTES
                )
                "pomiarze tętna" -> dailyWorkRequest = PeriodicWorkRequest.Builder(
                    NotificationWorkerPulseMeasurement::class.java,
                    reminderFrequency,
                    TimeUnit.MINUTES,
                    6L,
                    TimeUnit.MINUTES
                )
                "pomiarze glukozy" -> dailyWorkRequest = PeriodicWorkRequest.Builder(
                    NotificationWorkerSugarMeasurement::class.java,
                    reminderFrequency,
                    TimeUnit.MINUTES,
                    6L,
                    TimeUnit.MINUTES
                )
                "pomiarze wagi" -> dailyWorkRequest = PeriodicWorkRequest.Builder(
                    NotificationWorkerWeightMeasurement::class.java,
                    reminderFrequency,
                    TimeUnit.MINUTES,
                    6L,
                    TimeUnit.MINUTES
                )
                "wzięciów leków" -> dailyWorkRequest = PeriodicWorkRequest.Builder(
                    NotificationWorkerMedicines::class.java,
                    reminderFrequency,
                    TimeUnit.MINUTES,
                    6L,
                    TimeUnit.MINUTES
                )
                "przygotowaniu posiłków" -> dailyWorkRequest = PeriodicWorkRequest.Builder(
                    NotificationWorkerFood::class.java,
                    reminderFrequency,
                    TimeUnit.MINUTES,
                    6L,
                    TimeUnit.MINUTES
                )
                "piciu wody" -> dailyWorkRequest = PeriodicWorkRequest.Builder(
                    NotificationWorkerWater::class.java,
                    reminderFrequency,
                    TimeUnit.MINUTES,
                    6L,
                    TimeUnit.MINUTES
                )
                else -> throw Exception("")
            }
            val test = binding.editTextReminderTime.text.toString()
            val reminderTime = LocalTime.parse(binding.editTextReminderTime.text.toString())
            val minutesBetween = abs(ChronoUnit.MINUTES.between(LocalTime.now(), reminderTime))
            dailyWorkRequest.setInitialDelay(minutesBetween, TimeUnit.MINUTES)
                .addTag("id:${userId}").addTag("description:${binding.editTextReminderInfo.text}")
            val workerBuild = dailyWorkRequest.build()
            WorkManager.getInstance(this).enqueue(workerBuild)
            val workerID = workerBuild.id

            value.put(TableInfo.TABLE_REMINDER_COLUMN_TYPE, reminderType.selectedItem.toString())
            value.put(TableInfo.TABLE_REMINDER_COLUMN_WORKER_MOST, workerID.mostSignificantBits)
            value.put(TableInfo.TABLE_REMINDER_COLUMN_WORKER_LEAST, workerID.leastSignificantBits)
            value.put(TableInfo.TABLE_REMINDER_COLUMN_DESCRIPTION, reminderDesc.text.toString())
            value.put(TableInfo.TABLE_RESULT_COLUMN_USER, userId)
            val dataBaseHelper = DataBaseHelper(applicationContext)
            val db = dataBaseHelper.writableDatabase;
            db.insertOrThrow(TableInfo.TABLE_REMINDER, null, value)
            Toast.makeText(applicationContext, "Zapisano wynik", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                applicationContext,
                "Wszystkie pola muszą być wypełnione",
                Toast.LENGTH_SHORT
            ).show()
        }

        val intent = Intent(applicationContext, UserActivity::class.java)
        intent.putExtra("user_id", userId)
        startActivity(intent)

    }

}