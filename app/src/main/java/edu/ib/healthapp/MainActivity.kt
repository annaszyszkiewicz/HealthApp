package edu.ib.healthapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import edu.ib.healthapp.activity.NewUserActivity
import edu.ib.healthapp.databinding.ActivityMainBinding
import edu.ib.healthapp.worker.NotificationWorkerPressureMeasurement

class MainActivity : AppCompatActivity() {

    companion object {
        val PRESSURE_MEASUREMENT_CHANEL_ID = "idPressureMeasurement"
        val PULSE_CHANEL_ID = "idPulse"
        val SUGAR_CHANEL_ID = "idSugar"
        val WEIGHT_CHANEL_ID = "idWeight"
        val MEDICINES_CHANEL_ID = "idMedicines"
        val FOOD_CHANEL_ID = "idFood"
        val WATER_CHANEL_ID = "idWater"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createPressureMeasurementNotificationChannel()
        createFoodNotificationChannel()
        createMedicinestNotificationChannel()
        createPulseNotificationChannel()
        createSugarNotificationChannel()
        createWeightNotificationChannel()
        createWaterNotificationChannel()

//        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(NotificationWorkerPressureMeasurement::class.java)
//            .addTag("user_id:1")
//            .addTag("description:napis")
//            .build();
//        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

        // WorkManager.getInstance(this).cancelAllWork()
        WorkManager.getInstance(this).pruneWork()
    }

    fun onClickCreateUser(view: View) {
        val intent = Intent(applicationContext, NewUserActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        binding.recyclerUsers.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerUsers.adapter = UserAdapter(applicationContext, db)
    }

    private fun createPressureMeasurementNotificationChannel() {
        val name = getString(R.string.channel_name_pressure_measurement)
        val descriptionText = getString(R.string.channel_description_pressure_measurement)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(PRESSURE_MEASUREMENT_CHANEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createPulseNotificationChannel() {
        val name = getString(R.string.channel_name_pulse)
        val descriptionText = getString(R.string.channel_description_pulse)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(PULSE_CHANEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createSugarNotificationChannel() {
        val name = getString(R.string.channel_name_sugar)
        val descriptionText = getString(R.string.channel_description_sugar)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(SUGAR_CHANEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createWeightNotificationChannel() {
        val name = getString(R.string.channel_name_weight)
        val descriptionText = getString(R.string.channel_description_weight)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(WEIGHT_CHANEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createMedicinestNotificationChannel() {
        val name = getString(R.string.channel_name_medicines)
        val descriptionText = getString(R.string.channel_description_medicines)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(MEDICINES_CHANEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createFoodNotificationChannel() {
        val name = getString(R.string.channel_name_food)
        val descriptionText = getString(R.string.channel_description_food)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(FOOD_CHANEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createWaterNotificationChannel() {
        val name = getString(R.string.channel_name_water)
        val descriptionText = getString(R.string.channel_description_water)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(WATER_CHANEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


}