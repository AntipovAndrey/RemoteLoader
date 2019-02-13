package ru.andrey.data.api

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*
import ru.andrey.data.api.request.DeviceFilesInfoRequest
import ru.andrey.data.api.request.DeviceRequest
import ru.andrey.data.api.response.CommandResponse

interface RemoteLoaderApi {

    companion object {
        const val UPLOADING_FILE_KEY: String = "file"
    }

    @POST("/devices/save")
    fun saveDevice(@Body device: DeviceRequest): Completable

    @GET("/commands/pending/{deviceId}")
    fun getPending(@Path("deviceId") deviceId: String): Single<List<CommandResponse>>

    @POST("/paths/save")
    fun savePaths(@Body files: DeviceFilesInfoRequest): Completable

    @POST("/commands/fail")
    fun failCommand(
        @Query("device") deviceId: String,
        @Query("command") commandId: String
    ): Completable

    @Multipart
    @POST("/files/upload")
    fun uploadFile(
        @Query("device") deviceId: String,
        @Query("command") commandId: String,
        @Header("path") path: String,
        @Part files: MultipartBody.Part
    ): Completable
}
