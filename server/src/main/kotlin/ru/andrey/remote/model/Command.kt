package ru.andrey.remote.model

import org.springframework.data.annotation.Id

data class Command(val device: Device,
                   val action: Action,
                   val payload: List<String> = emptyList(),
                   val completed: Boolean = false) {
    @field:Id
    var id: String? = null
}
