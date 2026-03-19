package com.deinname.mixersreise.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.deinname.mixersreise.data.SettingsManager

class MixerWorker(val ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val channelId = "mixer_status_channel"

    override fun doWork(): Result {
        val settings = SettingsManager(ctx)

        // Wir prüfen: Läuft gerade schon eine "Bestrafungs-Zeit"?
        // Wenn nicht, und Mixer braucht etwas, dann schicken wir die Nachricht.
        if (settings.notificationSentTime == 0L) {

            // Hier setzen wir den Startschuss für die 5-Minuten-Frist
            settings.notificationSentTime = System.currentTimeMillis()

            sendNotification(
                "Mixer braucht dich!",
                "Hunger oder Langeweile? Schau nach ihm, bevor die Punkte sinken!"
            )
        }

        return Result.success()
    }

    private fun sendNotification(title: String, message: String) {
        val manager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification Channel erstellen (Wichtig für Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Mixer Status Updates",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Benachrichtigungen über Mixers Bedürfnisse"
            }
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(ctx, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Hier dein Mixer-Icon nutzen
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // Verschwindet beim Anklicken
            .build()

        manager.notify(1, notification)
    }
}