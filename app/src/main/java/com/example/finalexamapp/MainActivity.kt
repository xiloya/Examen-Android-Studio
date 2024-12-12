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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Room database
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        // Toast message
        Toast.makeText(this, getString(R.string.toast_message), Toast.LENGTH_LONG).show()

        // Button listeners
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

        // Button to insert data into the database
        findViewById<Button>(R.id.buttonInsertData).setOnClickListener {
            insertDataToDatabase()
        }

        // Button to fetch data from the database
        findViewById<Button>(R.id.buttonFetchData).setOnClickListener {
            fetchDataFromDatabase()
        }

        // RecyclerView setup
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter()
    }

    private fun showNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "exam_channel"

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

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun scheduleBackgroundTask() {
        val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)
        Log.d("MainActivity", "Tâche en arrière-plan planifiée")
    }

    // Insert data into Room database
    private fun insertDataToDatabase() {
        val userDao = db.userDao() // Get the DAO from the database
        val user = User(id = 1, name = "John Doe")
        Thread {
            userDao.insert(user)
            Log.d("MainActivity", "User inserted into the database")
        }.start()
    }

    // Fetch data from  database
    private fun fetchDataFromDatabase() {
        val userDao = db.userDao()
        Thread {
            val users = userDao.getAll()
            for (user in users) {
                Log.d("MainActivity", "User: ${user.name}")
            }
        }.start()
    }
}
