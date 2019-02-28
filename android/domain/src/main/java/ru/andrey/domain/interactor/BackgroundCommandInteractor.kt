package ru.andrey.domain.interactor

interface BackgroundCommandInteractor {

    /**
     *  Starts background listening for new commands and processing them
     */
    fun startBackgroundObserving()
}