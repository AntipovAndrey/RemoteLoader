package ru.andrey.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import ru.andrey.data.api.RemoteLoaderApi
import ru.andrey.data.api.request.DeviceRequest
import ru.andrey.data.api.response.CommandResponse
import ru.andrey.domain.model.Action
import ru.andrey.domain.model.Command
import ru.andrey.domain.repository.RemoteCommandRepository
import java.util.*
import java.util.concurrent.TimeUnit

class RemoteCommandRepositoryImpl(
    context: Context,
    private val api: RemoteLoaderApi
) : RemoteCommandRepository {

    companion object {
        private const val SHARED_PREF_NAME = "device_id_sp"
        private const val DEVICE_ID_KEY = "device_id_uuid"
        private const val TAG = "RemoteCommandRepo"
    }

    private val prefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

    override fun observeCommands(): Observable<List<Command>> {
        val repeater = Flowable.interval(0, 5, TimeUnit.SECONDS).onBackpressureDrop()

        return sendDeviceIfNeeded()
            .toFlowable<List<Command>>()
            .flatMap {
                api.getPending(getDeviceId())
                    .repeatWhen { handler -> handler.flatMap { repeater } }
                    .map { commands -> commands.map { toModel(it) } }
                    .flatMap { processCommands(it) }
                    .doOnNext { Log.i(TAG, "observeCommands:doOnNext ${it.toString()}") }
            }
            .retryWhen { handler ->
                handler.flatMap {
                    Log.e(TAG, "observeCommands:retryWhen", it)
                    repeater
                }
            }
            .toObservable()
    }

    // TODO: handle commands here
    private fun processCommands(commands: List<Command>): Flowable<List<Command>> {
        return Flowable.just(commands)
    }

    private fun sendDeviceIfNeeded(): Completable {
        return if (prefs.contains(DEVICE_ID_KEY)) {
            Completable.complete()
        } else {
            val uuid = UUID.randomUUID().toString()
            api.saveDevice(DeviceRequest(uuid, Build.MODEL))
                .doOnComplete { prefs.edit().putString(DEVICE_ID_KEY, uuid).apply() }
        }
    }

    private fun getDeviceId(): String {
        return prefs.getString(DEVICE_ID_KEY, null) ?: throw IllegalStateException()
    }

    private fun toModel(response: CommandResponse): Command {
        return Command(
            response.id,
            response.deviceId,
            Action.valueOf(response.action)
        )
    }
}
