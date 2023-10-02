package com.example.database



import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat

import com.example.database.Util.Companion.NOTIFICATION
import com.example.database.Util.Companion.NOTIFICATION_LIST



/*
Android13: We have full control when we want to ask the user for permission
Android 12L or lower: The system will show the permission dialog when the app creates its first notification channel*/

// the notification and channel can be created here
class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33
            intent.getParcelableExtra(NOTIFICATION, Notification::class.java)
        } else {
            intent.getParcelableExtra<Notification>(NOTIFICATION)
        }
        val notificationList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33
            intent.getParcelableArrayListExtra<ToDo>(NOTIFICATION_LIST, ToDo::class.java) // need to get list of class
        } else {
            intent.getParcelableArrayListExtra<ToDo>(NOTIFICATION_LIST)
        }
        if(notificationList?.isNotEmpty() == true){
            notificationList.forEach{
                if(it.date != null && it.time != null){
                    //val notification =  createNotification(it,context)
                    createChannel(getUniqueId().toString(),context,it)
                }
            }
        }
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        // Show the notification
        if (notification != null) {
            notificationManager.notify(getUniqueId().toInt(), notification) // notification id
        }
    }
}

@SuppressLint("UnspecifiedImmutableFlag")
fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
) {}




//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//
//
//class AlarmReciever : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        Log.d("AlarmReceiver", "Received alarm intent")
//        val notificationIntent = Intent(context, NotificationActivity::class.java)
//        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            0,
//            notificationIntent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        val builder = NotificationCompat.Builder(context, "androidknowledge")
//            .setSmallIcon(R.drawable.baseline_notifications_active_24)
//            .setContentTitle("Good Morning!")
//            .setContentText("It's time to wake up")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(123, builder.build())
//    }
//}