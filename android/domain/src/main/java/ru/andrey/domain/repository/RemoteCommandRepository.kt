package ru.andrey.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import ru.andrey.domain.model.Command
import ru.andrey.domain.model.FileInfo

interface RemoteCommandRepository {

    fun observeCommands(): Observable<List<Command>>

    fun handleFilesList(files: List<FileInfo>, command: Command): Completable

    fun failCommand(command: Command): Completable
}
