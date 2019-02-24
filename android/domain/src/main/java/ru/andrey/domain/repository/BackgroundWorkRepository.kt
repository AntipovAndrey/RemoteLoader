package ru.andrey.domain.repository

interface BackgroundWorkRepository {

    fun doInBackground(task: () -> Unit)
}