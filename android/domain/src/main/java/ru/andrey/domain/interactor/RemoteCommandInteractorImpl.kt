package ru.andrey.domain.interactor

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import ru.andrey.common.IO_SCHEDULER
import ru.andrey.domain.model.Action
import ru.andrey.domain.model.Command
import ru.andrey.domain.model.FileInfo
import ru.andrey.domain.repository.DeviceRepository
import ru.andrey.domain.repository.RemoteCommandRepository
import javax.inject.Inject
import javax.inject.Named

class RemoteCommandInteractorImpl @Inject constructor(
    @Named(IO_SCHEDULER) private val ioScheduler: Scheduler,
    private val remoteCommandRepository: RemoteCommandRepository,
    private val deviceRepository: DeviceRepository
) : RemoteCommandInteractor {

    override fun observeProcessedCommands(): Observable<List<Command>> = remoteCommandRepository.observeCommands()
        .subscribeOn(ioScheduler)
        .flatMapSingle { commands ->
            processCommands(commands).toSingle { commands }
        }


    override fun processCommands(commands: List<Command>): Completable {
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
