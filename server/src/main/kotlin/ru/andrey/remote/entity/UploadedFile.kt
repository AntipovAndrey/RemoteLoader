package ru.andrey.remote.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UploadedFile(
        val deviceId: String,
        val commandId: String,
        val path: String,
        val file: String
) {

    @field:Id
    var id: String? = null
}
