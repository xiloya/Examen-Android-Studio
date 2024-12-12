package com.example.finalexamapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Displaying a Toast message
        Toast.makeText(this, getString(R.string.toast_message), Toast.LENGTH_LONG).show()

        // Set up button listeners
        findViewById<Button>(R.id.buttonSnackbar).setOnClickListener {
            val rootView = findViewById<View>(android.R.id.content)
            Snackbar.make(rootView, getString(R.string.snackbar_message), Snackbar.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.buttonScheduleTask).setOnClickListener {
            scheduleBackgroundTask()
        }

        findViewById<Button>(R.id.buttonNotification).setOnClickListener {
            showNotification()
        }
    }

    private fun showNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "exam_channel"

        // Create a Notification Channel for Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Exam Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for FinalExamApp notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Build the Notification
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Show the Notification
        notificationManager.notify(1, notification)
    }

    private fun scheduleBackgroundTask() {
        // Create a one-time WorkRequest
        val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()

        // Enqueue the WorkRequest
        WorkManager.getInstance(this).enqueue(workRequest)
        Log.d("MainActivity", "Tâche en arrière-plan planifiée")
    }
}
