package ru.andrey.data.repository

import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.andrey.data.worker.CommonWorker
import ru.andrey.data.worker.CommonWorker.Companion.ID_KEY
import ru.andrey.data.worker.WorkersRegistry
import ru.andrey.domain.repository.BackgroundWorkRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkManagerBackgroundWorkRepository @Inject constructor() : BackgroundWorkRepository {

    override fun repeatInBackground(name: String, task: () -> Unit) {
        val work = PeriodicWorkRequestBuilder<CommonWorker>(5, TimeUnit.MINUTES)
            .setInputData(Data.Builder().putString(ID_KEY, name).build())
            .build()

        WorkersRegistry.registerTask(name, task)

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(name, ExistingPeriodicWorkPolicy.KEEP, work)
    }
}