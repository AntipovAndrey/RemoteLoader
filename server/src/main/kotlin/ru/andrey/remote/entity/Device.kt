package ru.andrey.remote.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Device(
        @field:Indexed(unique = true)
        val deviceId: String,
        val name: String
) {

    @field:Id
    var id: String? = null
}
