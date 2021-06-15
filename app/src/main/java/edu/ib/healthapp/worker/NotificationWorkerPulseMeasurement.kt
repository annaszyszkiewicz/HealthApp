package edu.ib.healthapp.worker

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import edu.ib.healthapp.DataBaseHelper
import edu.ib.healthapp.MainActivity
import edu.ib.healthapp.R
import edu.ib.healthapp.TableInfo
import edu.ib.healthapp.activity.ResultsActivity
import java.time.LocalTime

class NotificationWorkerPulseMeasurement (private val context: Context, val params: WorkerParameters): Worker(context,params) {
    override fun doWork(): Result {
        if(!(LocalTime.now().hour >= 22 || LocalTime.now().hour <= 6)){
            sendNotification()
        }
        return Result.success()
    }


    private fun sendNotification() {

        val tags=params.tags.iterator()
        val usertag = tags.next().split(":")[1]
        val descriptiontag = tags.next().split(":")[1]

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val cursor = db.query(
            TableInfo.TABLE_USER, arrayOf(TableInfo.TABLE_USER_COLUMN_NAME, TableInfo.TABLE_USER_COLUMN_SURNAME),
            TableInfo.TABLE_USER_ID + "=?", arrayOf(usertag),
            null, null, null
        )
        cursor.moveToNext()
        val userName = cursor.getString(0)
        val userSurname = cursor.getString(1)

        cursor.close()

        val intent = Intent(context, ResultsActivity::class.java)
        intent.putExtra("user_id", usertag.toInt())

        val resultPendingIntent : PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        var builder = NotificationCompat.Builder(context, MainActivity.PULSE_CHANEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("$userName $userSurname zmierz tętno")
            .setContentText(descriptiontag)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)){
            notify(0,builder.build())
        }
    }
}