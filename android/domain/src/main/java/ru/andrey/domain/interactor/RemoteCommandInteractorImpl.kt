package ru.andrey.domain.interactor

import ru.andrey.domain.repository.RemoteCommandRepository

class RemoteCommandInteractorImpl(
    private val repository: RemoteCommandRepository
) : RemoteCommandInteractor {

    override fun observeCommands() = repository.observeCommands()
}
