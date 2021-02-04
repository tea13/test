package com.example.lab1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*
import kotlin.concurrent.schedule
import android.animation.Animator
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val channelId = "channel_id_example"
    val notificationID = 101
    var cameraId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fade_in: Button = findViewById(R.id.fade_in)
        fade_in.setOnClickListener {
            textView2.visibility = View.VISIBLE
            val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            textView2.startAnimation(animation)
        }

        val fade_out: Button = findViewById(R.id.fade_out)
        fade_out.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            textView2.startAnimation(animation)
            Handler().postDelayed({
                textView2.visibility = View.GONE
            }, 1000)
        }



        val camera_btn: Button = findViewById(R.id.camera_btn)
        camera_btn.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("CAMERA_ID", cameraId)
            startActivity(intent)
            finish()
        }


        val search_text: EditText = findViewById(R.id.search_text)
        val search_btn: Button = findViewById(R.id.search_btn)
        search_btn.setOnClickListener {
            val text = search_text.text.toString()
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, text)
            startActivity(intent);
        }


        val push_notification: Button = findViewById(R.id.push)
        createNotificationChannel()
        push_notification.setOnClickListener {
            Timer("Notification", false).schedule(10000){
                sendNotification()
            }
        }


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "You are notified!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
                enableVibration(true)
            }
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_wb_sunny_24)
                .setContentTitle("Notification Title")
                .setContentText("You are notified!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
        with(NotificationManagerCompat.from(this)) {
            notify(notificationID, builder.build())
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioButton ->
                    if (checked) {
                        cameraId = 1
                    }
                R.id.radioButton2 ->
                    if (checked) {
                        cameraId = 0
                    }
            }
        }
    }




}