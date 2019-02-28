package ru.andrey.domain.interactor

import io.reactivex.Completable
import io.reactivex.Observable
import ru.andrey.domain.model.Command

interface RemoteCommandInteractor {

    /**
     *  Subscribes to a command emitter, processes received commands, returns commands to downstream
     */
    fun observeProcessedCommands(): Observable<List<Command>>

    /**
     *  Processes command set and  completes
     */
    fun processCommands(commands: List<Command>): Completable
}