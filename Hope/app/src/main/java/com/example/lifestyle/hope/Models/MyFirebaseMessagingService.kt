package com.example.lifestyle.hope.Models

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.lifestyle.hope.Activity.MainActivity
import com.example.lifestyle.hope.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.lang.Exception

open class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        Log.d("TAG1", "From: ${p0?.from}")
        showNotificationChannel(p0!!.notification?.title.toString(), p0.notification?.body.toString())
        p0?.data?.isNotEmpty()?.let {
            Log.d("TAG2", "Message data payload: " + p0.data)
            if (true) {

            } else {
            }
        }
        p0?.notification?.let {
            Log.d("TAG3", "Message Notification Body: ${it.body}")
        }

    }
    fun showNotification(title:String , body: String){
        val intent : Intent = Intent(this,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent : PendingIntent = PendingIntent.getActivities(this,0, arrayOf(intent),PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(this)
        notificationBuilder.setContentTitle("")
        notificationBuilder.setContentText("")
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setSmallIcon(R.drawable.ic_homenav)
        notificationBuilder.setContentIntent(pendingIntent)
        val notificationManager :NotificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0,notificationBuilder.build())
    }
    fun showNotificationChannel(title: String , body: String){
        val intent : Intent = Intent(this,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent : PendingIntent = PendingIntent.getActivities(this,0, arrayOf(intent),PendingIntent.FLAG_ONE_SHOT)

        val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "com.example.lifestyle.hope"

        if(Build.VERSION.SDK_INT  >=  Build.VERSION_CODES.O){
            val notificationChannel : NotificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description ="EDMT Channel"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.vibrationPattern = longArrayOf(100,200,300,400,500,600,700,800)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(this)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(body)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setSmallIcon(R.drawable.ic_homenav)
        notificationBuilder.setContentIntent(pendingIntent)
        notificationManager.notify(0,notificationBuilder.build())
    }
    override fun onMessageSent(p0: String?) {
        super.onMessageSent(p0)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    override fun onSendError(p0: String?, p1: Exception?) {
        super.onSendError(p0, p1)
    }

    override fun onNewToken(p0: String?) {
        Log.e("TOKEN",p0)
        super.onNewToken(p0)
    }
    fun sendRegistrationToServer(toke:String){

    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}