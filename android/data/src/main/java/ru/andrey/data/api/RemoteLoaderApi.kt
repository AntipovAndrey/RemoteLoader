package ru.andrey.data.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.andrey.data.api.request.DeviceRequest
import ru.andrey.data.api.response.CommandResponse

interface RemoteLoaderApi {

    @POST("/devices/save")
    fun saveDevice(@Body device: DeviceRequest): Completable

    @GET("/devices/pending/{deviceId}")
    fun getPending(@Path("deviceId") deviceId: String): Single<List<CommandResponse>>
}
