package ru.andrey.remote.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UploadedFiles(
        val deviceId: String,
        val commandId: String,
        val files: List<File>
) {

    @field:Id
    var id: String? = null
}

data class File(val location: String)
