package ru.andrey.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.andrey.domain.model.Command
import ru.andrey.domain.model.FileInfo
import java.io.File

interface RemoteCommandRepository {

    /**
     *  Starts polling for a new list of pending commands
     */
    fun observeCommands(): Observable<List<Command>>

    /**
     *  Manually asks the server for a new commands.
     *  If it is the first invocation, it sends a device id first
     */
    fun getPendingCommands(): Single<List<Command>>

    /**
     *  Handles [ru.andrey.domain.model.Action.QUERY_LIST] command
     */
    fun handleFilesList(files: List<FileInfo>, command: Command): Completable

    /**
     *  Handles [ru.andrey.domain.model.Action.FETCH_FILE] command
     */
    fun handleFile(file: File, command: Command): Completable

    /**
     *  Sends a fail request to the server in case of error
     */
    fun failCommand(command: Command): Completable
}
