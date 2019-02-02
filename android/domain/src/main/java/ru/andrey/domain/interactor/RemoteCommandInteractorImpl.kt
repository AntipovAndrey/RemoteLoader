package ru.andrey.domain.interactor

import io.reactivex.Observable
import io.reactivex.Scheduler
import ru.andrey.domain.model.Command
import ru.andrey.domain.repository.RemoteCommandRepository

class RemoteCommandInteractorImpl(
    private val ioScheduler: Scheduler,
    private val repository: RemoteCommandRepository
) : RemoteCommandInteractor {

    override fun observeCommands(): Observable<List<Command>> = repository.observeCommands().subscribeOn(ioScheduler)
}
