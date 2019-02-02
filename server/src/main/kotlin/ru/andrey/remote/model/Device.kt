package ru.andrey.remote.model

import org.springframework.data.annotation.Id

data class Device(val deviceId: String) {

    @field:Id
    var id: String? = null
}
