package com.deinname.mixersreise.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.deinname.mixersreise.data.SettingsManager

class MixerWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val settingsManager = SettingsManager(applicationContext)

        // R6: Logik für Hintergrund-Updates (z.B. Hunger oder Bonus-Herzen)
        val currentHearts = settingsManager.getHearts()

        // Beispiel: Ein kleiner Bonus, wenn die App im Hintergrund läuft
        settingsManager.saveHearts(currentHearts + 1)

        return Result.success()
    }
}