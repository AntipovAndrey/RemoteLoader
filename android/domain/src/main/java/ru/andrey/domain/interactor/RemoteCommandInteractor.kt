package ru.andrey.domain.interactor

import io.reactivex.Observable
import ru.andrey.domain.model.Command

interface RemoteCommandInteractor {

    fun observeCommands(): Observable<List<Command>>
}