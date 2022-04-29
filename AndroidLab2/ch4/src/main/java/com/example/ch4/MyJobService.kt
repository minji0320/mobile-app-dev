package com.example.ch4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MyJobService : JobService() {

    override fun onStartJob(p0: JobParameters?): Boolean {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //channel 개념 적용해서 builder 만들어야 한다.. 안그러면 안뜬다..
            val channelId="oneChannel"
            val channel = NotificationChannel(channelId, "My Channel", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description="My Description"

            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(this, channelId)
        }else {
            //하위 버전은 channel 개념 없다..
            builder = NotificationCompat.Builder(this)
        }

        //Builder... -> Notification 만들고(정보) -> NotificationManager 가 띄운다..
        builder.run {
            setSmallIcon(android.R.drawable.ic_notification_overlay)
            setWhen(System.currentTimeMillis())
            setContentTitle("My Message")
            setContentText("안녕하세요..")
        }

        //발생..
        manager.notify(11, builder.build())

        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }
}