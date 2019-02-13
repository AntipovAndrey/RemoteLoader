package ru.andrey.remote.controller.request

data class UploadFileRequest(
        val deviceId: String,
        val commandId: String,
        val path: String,
        val file: String
)