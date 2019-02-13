package ru.andrey.remote.controller.response

data class UploadedFile(
        val deviceId: String,
        val commandId: String,
        val path: String,
        val file: String
)
