package com.example.scheduleapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scheduleapp.databinding.ActivityMainBinding
import com.google.android.material.timepicker.TimeFormat
import java.text.DateFormat
import java.util.Calendar
import java.util.Date


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()
        binding.submitButton.setOnClickListener{ scheduleNotification()}

    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification() {

    val intent = Intent(applicationContext, Notification::class.java)
        val title = binding.tittleET.text.toString()
        val message = binding.messageET.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, title)

        val pendingIntent = PendingIntent.getBroadcast(

            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_MUTABLE

        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time,title,message)




    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notificarion Schedule")
            .setMessage("Title: "+ title +
            "\nMessage: " + message +
            "\nAT" + dateFormat.format(date)+ " " + timeFormat.format(date))

            .setPositiveButton("Okey"){_,_ ->}
            .show()



    }

    private fun getTime(): Long {

        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.DatePicker.dayOfMonth
        val  month = binding.DatePicker.month
        val year = binding.DatePicker.year


        val calendar = Calendar.getInstance()
        calendar.set(year,month,day,hour,minute)

        return calendar.timeInMillis

    }

    private fun createNotificationChannel() {

        val name = "Notif Channel"
        val desc = "A description channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID,name,importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}