package ru.andrey.domain.repository

interface BackgroundWorkRepository {

    /**
     *  Specifies a background recurring task to be executed by a given name
     *  Only the first task with the same name will be set to execution
     *
     *  //todo: tweak time
     */
    fun repeatInBackground(name: String, task: () -> Unit)
}