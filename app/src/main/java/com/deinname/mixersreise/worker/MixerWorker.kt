package com.deinname.mixersreise.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.deinname.mixersreise.data.SettingsManager

class MixerWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val settings = SettingsManager(applicationContext)

        // FAKTEN-CHECK: Dein SettingsManager nutzt getHearts() und saveHearts()
        // Wir laden den aktuellen Wert, verringern ihn (Beispiel-Logik für Hunger/Vernachlässigung)
        // und speichern ihn wieder ab.

        val currentHearts = settings.getHearts()

        // Beispiel: Mixer verliert über Zeit Herzen, wenn man sich nicht kümmert
        if (currentHearts > 0) {
            settings.saveHearts(currentHearts - 1)
        }

        return Result.success()
    }
}