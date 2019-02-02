package ru.andrey.domain.model

data class Command(
    var id: String,
    val deviceId: String,
    val action: Action,
    val payload: String? = null
)
