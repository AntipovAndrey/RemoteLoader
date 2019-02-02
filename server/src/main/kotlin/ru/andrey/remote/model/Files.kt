package ru.andrey.remote.model

import org.springframework.data.annotation.Id

data class Files(val device: Device, val paths: List<String>) {

    @field:Id
    var id: String? = null
}
