package ru.andrey.remote.controller.response

data class UploadedFiles(
        val deviceId: String,
        val commandId: String,
        val files: List<FileLocation>
)

data class FileLocation(val location: String)
