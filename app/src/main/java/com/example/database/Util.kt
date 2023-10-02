package com.example.database


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.database.R
import com.example.database.ToDo
import com.example.database.AlarmReceiver
import java.util.*

class Util {
    companion object {
        const val NOTIFICATION = "notification"
        const val NOTIFICATION_LIST = "NOTIFICATION_LIST"
        const val CHANNEL_NAME = "ToDoReminder"
        const val CHANNEL_ID = "0"
    }
}

@SuppressLint("UnspecifiedImmutableFlag")
fun createChannel(channelId: String, context: Context, toDo: ToDo) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val (hours, min) = toDo.time!!.split(":").map { it.toInt() }
        val (year, month, day) = toDo.date!!.split("-").map { it.toInt() }
        val calendar = Calendar.getInstance()
        val notificationIntent = Intent(context, TodoList::class.java)
        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getBroadcast(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        val iconDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.baseline_notifications_active_24)!!
        val iconBitmap: Bitmap = iconDrawable.toBitmap()

        val contentIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val nm: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        calendar.set(year, month - 1, day, hours, min, 0)
        if (calendar.timeInMillis > System.currentTimeMillis()) {
            val sb: Spannable = SpannableString(toDo.title)
            sb.setSpan(StyleSpan(Typeface.BOLD), 0, sb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setChannelId(channelId)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setWhen(calendar.timeInMillis)
                .setContentIntent(contentIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .addLine(toDo.description)
                        .setBigContentTitle(sb)
                        .setSummaryText("ToDo Reminder")
                )
                .build()
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(Util.NOTIFICATION, notificationBuilder)

            val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context,
                    toDo.id,
                    intent,
                    PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    context, toDo.id, intent, PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            alarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
}

fun createNotification(toDo: ToDo, context: Context): Notification {
    val notificationBuilder = NotificationCompat.Builder(context, toDo.id.toString())
        .setSmallIcon(R.drawable.baseline_notifications_active_24)
        .setContentTitle(toDo.title)
        .setContentText(toDo.description)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    return notificationBuilder.build()
}

fun cancelAlarm(context: Context, id: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
    } else {
        PendingIntent.getBroadcast(
            context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
    alarmManager.cancel(pendingIntent)
}

fun String.capitalized(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}

fun getUniqueId() = ((System.currentTimeMillis() % 1000000).toLong())
