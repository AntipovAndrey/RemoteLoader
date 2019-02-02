package ru.andrey.remote.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class Command(val deviceId: String,
                   val action: Action,
                   val payload: String? = null,
                   var completed: Boolean = false,
                   var failed: Boolean = false,
                   val time: Instant = Instant.now()) {
    @field:Id
    var id: String? = null
}
