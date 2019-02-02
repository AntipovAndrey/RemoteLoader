package ru.andrey.domain.repository

import io.reactivex.Observable
import ru.andrey.domain.model.Command

interface RemoteCommandRepository {

    fun observeCommands(): Observable<List<Command>>
}
