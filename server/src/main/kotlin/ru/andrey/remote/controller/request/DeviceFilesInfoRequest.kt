package ru.andrey.remote.controller.request

import javax.validation.constraints.NotEmpty

data class DeviceFilesInfoRequest(

        @field:NotEmpty
        val deviceId: String?,

        @field:NotEmpty
        val commandId: String?,

        @field:NotEmpty
        val filesInfo: List<FileInfoRequest>
)

data class FileInfoRequest(val path: String, val size: Long?)
