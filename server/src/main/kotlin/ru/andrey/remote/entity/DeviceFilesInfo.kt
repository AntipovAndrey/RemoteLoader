package ru.andrey.remote.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class DeviceFilesInfo(val deviceId: String,
                           val commandId: String,
                           val filesInfo: List<FileInfo>,
                           var expired: Boolean = false,
                           val time: Instant = Instant.now()) {

    @field:Id
    var id: String? = null
}

data class FileInfo(val path: String, val size: Long?)
