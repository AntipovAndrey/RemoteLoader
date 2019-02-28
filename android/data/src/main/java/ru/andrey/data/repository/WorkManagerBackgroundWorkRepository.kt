package ru.andrey.data.repository

import ru.andrey.domain.repository.BackgroundWorkRepository
import javax.inject.Inject

class WorkManagerBackgroundWorkRepository @Inject constructor() : BackgroundWorkRepository {

    override fun repeatInBackground(task: () -> Unit) {

    }
}