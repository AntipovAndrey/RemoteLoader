package ru.andrey.data.api.request

data class DeviceFilesInfoRequest(
    val deviceId: String,
    val commandId: String,
    val filesInfo: List<FileInfoRequest>
)

data class FileInfoRequest(val path: String, val size: Long?)
