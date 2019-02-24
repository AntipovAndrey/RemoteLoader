package ru.andrey.domain.interactor

import ru.andrey.domain.repository.BackgroundWorkRepository

class BackgroundCommandInteractorImpl(
    private val remoteCommandInteractor: RemoteCommandInteractor,
    private val backgroundWorkRepository: BackgroundWorkRepository
) : BackgroundCommandInteractor {

    override fun startBackgroundObserving() {
        backgroundWorkRepository.doInBackground {
            remoteCommandInteractor
                .observeProcessedCommands()
                .subscribe({}, {})
        }
    }
}