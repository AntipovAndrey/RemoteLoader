package ru.andrey.remote.controller.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
class CommandResponse(
        val id: String,
        val deviceId: String,
        val action: String,
        val payload: String?,
        val completed: Boolean,
        val failed: Boolean,
        val time: Instant)
