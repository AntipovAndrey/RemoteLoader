package ru.andrey.data.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.andrey.data.worker.CommonWorker.Companion.ID_KEY

/**
 *  Common worker that uses passed [ID_KEY] to lookup a task from the [WorkersRegistry]
 */
class CommonWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val workId = inputData.getString(ID_KEY)
            ?: return failWithMessage("work_id not found")

        val task = WorkersRegistry.findTask(workId)
            ?: return failWithMessage("$workId not found in the registry")

        task()

        return Result.success()
    }

    private fun failWithMessage(message: String) =
        Result.failure(Data.Builder().putString(ERROR_MESSAGE_KEY, message).build())

    companion object {
        const val ID_KEY = "work_id"
        const val ERROR_MESSAGE_KEY = "work_failed"
    }
}