package com.example.firebase.FCM


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification channel"
const val channelName = "com.example.firebase.FCM"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {

        message.data.let {
            generateNotification(it["title"], it["body"])
            Log.d("TAG", "Message data payload: ${message.data}")
        }

        Log.e("Done", message.notification?.title.toString())

        if(message.notification != null)
        {
            generateNotification(message.notification!!.title!!,message.notification!!.body!!)
        }
        else
        {
            Toast.makeText(this,"Error Occurred",Toast.LENGTH_SHORT).show()
        }

        super.onMessageReceived(message)
    }

    private fun generateNotification(title: String?, description: String?) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )


        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channelId).setSmallIcon(R.drawable.logo)
                .setAutoCancel(true).setVibrate(longArrayOf(1000, 1000, 1000, 10
                    ))
                .setOnlyAlertOnce(true).setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title!!, description!!))

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val notificationChannel=NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,builder.build())
    }

    private fun getRemoteView(title: String, description: String): RemoteViews {

        val remoteViews = RemoteViews("com.example.firebase.FCM", R.layout.notification)

        remoteViews.setTextViewText(R.id.Title, title)
        remoteViews.setTextViewText(R.id.description, description)
        remoteViews.setImageViewResource(R.id.app_logo, R.drawable.logo)

        return remoteViews
    }

}