package ru.andrey.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.andrey.data.api.RemoteLoaderApi
import ru.andrey.data.api.request.DeviceFilesInfoRequest
import ru.andrey.data.api.request.DeviceRequest
import ru.andrey.data.api.request.FileInfoRequest
import ru.andrey.data.api.response.CommandResponse
import ru.andrey.domain.model.Action
import ru.andrey.domain.model.Command
import ru.andrey.domain.model.FileInfo
import ru.andrey.domain.repository.RemoteCommandRepository
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.HashSet


class RemoteCommandRepositoryImpl @Inject constructor(
    context: Context,
    private val api: RemoteLoaderApi
) : RemoteCommandRepository {

    companion object {
        private const val SHARED_PREF_NAME = "device_id_sp"
        private const val DEVICE_ID_KEY = "device_id_uuid"
        private const val TAG = "RemoteCommandRepo"
    }

    private val prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

    private val commandInProcess = HashSet<String>()

    private val commandsObservable: Observable<List<Command>> by lazy {
        Flowable.interval(5, TimeUnit.SECONDS)
            .flatMapSingle { getPendingCommands() }
            .doOnNext { Log.i(TAG, "observeCommands:doOnNext $it") }
            .doOnError { Log.i(TAG, "observeCommands:doOnError ${it.message}") }
            .retry()
            .replay(1)
            .refCount()
            .toObservable()
    }

    override fun observeCommands() = commandsObservable

    override fun getPendingCommands(): Single<List<Command>> {
        return saveDeviceId()
            .flatMap { deviceId ->
                api.getPending(deviceId)
                    .map { commands -> commands.map { commandToModel(it) } }
            }
            .doOnSuccess { Log.i(TAG, "getPendingCommands:doOnSuccess $it") }
            .doOnError { Log.e(TAG, "getPendingCommands:doOnError ${it.message}") }
    }

    override fun handleFilesList(files: List<FileInfo>, command: Command): Completable =
        handleCommand(command) {
            api.savePaths(filesInfoToDto(files, command))
        }

    override fun handleFile(file: File, command: Command): Completable =
        handleCommand(command) {
            val requestBody = RequestBody.create(null, file)
            val part = MultipartBody.Part.createFormData(RemoteLoaderApi.UPLOADING_FILE_KEY, file.name, requestBody)
            api.uploadFile(getDeviceId(), command.id, file.absolutePath, part)
        }

    override fun failCommand(command: Command): Completable = api
        .failCommand(command.deviceId, command.id)
        .onErrorResumeNext { Completable.complete() }
        .doOnComplete { commandInProcess.remove(command.id) }

    private fun saveDeviceId(): Single<String> {
        return if (prefs.contains(DEVICE_ID_KEY)) {
            Single.just(getDeviceId())
        } else {
            Single.fromCallable { UUID.randomUUID() }
                .map { it.toString() }
                .map { DeviceRequest(it, Build.MODEL) }
                .flatMap { device ->
                    api.saveDevice(device).toSingle { device }
                }
                .map { it.deviceId }
                .doOnSuccess { id -> prefs.edit().putString(DEVICE_ID_KEY, id).apply() }
        }
    }

    private fun handleCommand(command: Command, handler: () -> Completable): Completable = Single
        .just(command)
        .filter { !commandInProcess.contains(it.id) }
        .flatMapCompletable {
            handler().doOnSubscribe { commandInProcess.add(command.id) }
        }
        .doOnError { Log.i(TAG, "handleCommand:doOnError ${it.message}") }
        .onErrorResumeNext { failCommand(command) }

    private fun getDeviceId(): String {
        return prefs.getString(DEVICE_ID_KEY, null) ?: throw IllegalStateException()
    }

    private fun filesInfoToDto(files: List<FileInfo>, command: Command): DeviceFilesInfoRequest {
        return DeviceFilesInfoRequest(
            command.deviceId,
            command.id,
            files.map { FileInfoRequest(it.path, it.size) }
        )
    }

    private fun commandToModel(response: CommandResponse): Command {
        return Command(
            response.id,
            response.deviceId,
            Action.valueOf(response.action),
            response.payload
        )
    }
}
