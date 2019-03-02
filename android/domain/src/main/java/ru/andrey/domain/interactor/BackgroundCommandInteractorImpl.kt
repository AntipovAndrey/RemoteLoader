package ru.andrey.domain.interactor

import ru.andrey.domain.repository.BackgroundWorkRepository
import ru.andrey.domain.repository.RemoteCommandRepository
import javax.inject.Inject

class BackgroundCommandInteractorImpl @Inject constructor(
    private val remoteCommandInteractor: RemoteCommandInteractor,
    private val backgroundWorkRepository: BackgroundWorkRepository,
    private val remoteCommandRepository: RemoteCommandRepository
) : BackgroundCommandInteractor {

    private val workName = "LISTEN_COMMANDS"

    override fun startBackgroundObserving() {
        backgroundWorkRepository.repeatInBackground(workName) {
            remoteCommandRepository.getPendingCommands()
                .flatMapCompletable { commands ->
                    remoteCommandInteractor.processCommands(commands)
                }
                .subscribe()
        }
    }
}