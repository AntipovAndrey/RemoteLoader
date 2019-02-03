package ru.andrey.remote.controller.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DeviceFilesInfoResponse(

        val deviceId: String,
        val commandId: String?,
        val filesInfo: List<FileInfoResponse>,
        val time: Instant
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FileInfoResponse(val path: String, val size: Long?)
