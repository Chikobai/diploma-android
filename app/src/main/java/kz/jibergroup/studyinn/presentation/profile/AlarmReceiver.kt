package kz.jibergroup.studyinn.presentation.profile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.presentation.main.MainActivity

const val LOCAL_NOTIFICATION_REMINDER = "LOCAL_NOTIFICATION_REMINDER"

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == LOCAL_NOTIFICATION_REMINDER) {
            val util = NotificationUtils()
            util.fireNotification(context)
        }
    }
}

class NotificationUtils() {
    fun fireNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            context, 0,
            intent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Напоминания")
            .setContentText("Пора заниматься!")
            .setChannelId("1")
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setContentIntent(contentIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "1",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}