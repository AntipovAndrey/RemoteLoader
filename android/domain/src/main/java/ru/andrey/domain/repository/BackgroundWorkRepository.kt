package ru.andrey.domain.repository

interface BackgroundWorkRepository {

    /**
     *  Specifies a background recurring task to be executed
     */
    fun repeatInBackground(task: () -> Unit)
}