package ru.andrey.domain.interactor

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import ru.andrey.domain.model.Action
import ru.andrey.domain.model.Command
import ru.andrey.domain.model.FileInfo
import ru.andrey.domain.repository.DeviceRepository
import ru.andrey.domain.repository.RemoteCommandRepository

class RemoteCommandInteractorImpl(
    private val ioScheduler: Scheduler,
    private val remoteCommandRepository: RemoteCommandRepository,
    private val deviceRepository: DeviceRepository
) : RemoteCommandInteractor {

    override fun observeProcessedCommands(): Observable<List<Command>> = remoteCommandRepository.observeCommands()
        .subscribeOn(ioScheduler)
        .flatMapSingle { processCommands(it) }

    private fun processCommands(commands: List<Command>): Single<List<Command>> {
        return Single.just(commands)
            .flatMapObservable { Observable.fromIterable(it) }
            .flatMapCompletable { command ->
                when (command.action) {
                    Action.QUERY_LIST -> {
                        handleQueryList(command)
                    }
                    Action.FETCH_FILE -> {
                        handleFetchFile(command)
                    }
                }
            }
            .toSingle { commands }

    }

    private fun handleQueryList(command: Command) = Single
        .fromCallable { deviceRepository.getDevicesFiles() }
        .map { filesList -> filesList.map { FileInfo(it.absolutePath, it.length()) } }
        .flatMapCompletable { remoteCommandRepository.handleFilesList(it, command) }
        .onErrorResumeNext { remoteCommandRepository.failCommand(command) }


    private fun handleFetchFile(command: Command) = Single
        .fromCallable { deviceRepository.getFile(command.payload!!) }
        .flatMapCompletable { remoteCommandRepository.handleFile(it, command) }
        .onErrorResumeNext { remoteCommandRepository.failCommand(command) }
}
