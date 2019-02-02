package ru.andrey.data.api.response

class CommandResponse(
    val id: String,
    val deviceId: String,
    val action: String,
    val payload: String? = null
)
