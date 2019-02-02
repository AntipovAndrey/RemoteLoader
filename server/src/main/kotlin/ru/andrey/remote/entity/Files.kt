package ru.andrey.remote.entity

import org.springframework.data.annotation.Id

data class Files(val deviceId: String, val paths: List<String>) {

    @field:Id
    var id: String? = null
}
