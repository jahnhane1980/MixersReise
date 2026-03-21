package com.deinname.mixersreise.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.deinname.mixersreise.data.SettingsManager

class MixerWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val settingsManager = SettingsManager(context)

    override fun doWork(): Result {
        // Beispiel-Logik: Mixer bekommt im Hintergrund Hunger/Sabber
        // Wir nutzen hier direkt die totalHearts Variable des SettingsManagers
        val currentHearts = settingsManager.totalHearts

        // Hier könnte später die Logik rein: Wenn 1 Stunde vergangen, Herzen -5
        // settingsManager.totalHearts = (currentHearts - 5).coerceAtLeast(0)

        return Result.success()
    }
}