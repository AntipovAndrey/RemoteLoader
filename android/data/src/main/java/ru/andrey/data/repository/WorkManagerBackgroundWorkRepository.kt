package ru.andrey.data.repository

import ru.andrey.domain.repository.BackgroundWorkRepository

class WorkManagerBackgroundWorkRepository : BackgroundWorkRepository {

    override fun doInBackground(task: () -> Unit) {

    }
}