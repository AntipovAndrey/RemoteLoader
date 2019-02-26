package ru.andrey.domain.interactor

import io.reactivex.Observable
import ru.andrey.domain.model.Command

interface RemoteCommandInteractor {

    fun observeProcessedCommands(): Observable<List<Command>>

    fun processCommands(commands: List<Command>): Completable
}