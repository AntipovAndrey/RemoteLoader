package ru.andrey.remote.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

data class Device(
        @field:Indexed(unique = true)
        val deviceId: String,
        val name: String
) {

    @field:Id
    var id: String? = null
}
