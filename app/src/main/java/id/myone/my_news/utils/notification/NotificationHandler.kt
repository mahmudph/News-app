/**
 * Created by Mahmud on 29/06/23.
 * mahmud120398@gmail.com
 */

package id.myone.my_news.utils.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.net.toUri
import id.myone.my_news.R
import androidx.core.app.NotificationCompat
import id.myone.my_news.MainActivity
import id.myone.my_news.common.Constraint
import id.myone.my_news.utils.config.RemoteConfigApp

class NotificationHandler(
    private val configApp: RemoteConfigApp,
    private val context: Context,
) {
    private lateinit var notificationManager: NotificationManager

    init { createNotificationChanel() }

    fun createNotificationData(
        title: String,
        description: String,
        pendingIntent: PendingIntent? = null,
    ): Notification {

        val builder = NotificationCompat.Builder(context, channelId)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.logo)
            .setContentText(description)
            .setContentTitle(title)
            .setAutoCancel(true)

        if (pendingIntent != null) builder.setContentIntent(pendingIntent)

        return builder.build()
    }

    fun notify(notification: Notification) {
        notifyId++
        notificationManager.notify(notifyId, notification)
    }

    private fun createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = channelDesc
            }

            notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }


    fun createPendingIntent(param: Int): PendingIntent {
        val hostUrl = configApp.getValue(Constraint.Config.deeplinkHostUri).asString()

        val url = "$hostUrl/news?id=$param"
        val taskDetailIntent = Intent(Intent.ACTION_VIEW, url.toUri(), context, MainActivity::class.java)

        return TaskStackBuilder.create(context).run {

            addNextIntentWithParentStack(taskDetailIntent)

            val pendingFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE
            } else PendingIntent.FLAG_UPDATE_CURRENT

            getPendingIntent(requestCode, pendingFlag)
        }
    }

    companion object {
        private var notifyId = 0
        private const val requestCode = 10
        private const val channelId = "my-news-app-id"
        private const val channelName = "my news channel name"
        private const val channelDesc = "my news description"
    }

}