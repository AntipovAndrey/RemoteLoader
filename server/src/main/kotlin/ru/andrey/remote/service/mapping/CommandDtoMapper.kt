package ru.andrey.remote.service.mapping

import ru.andrey.remote.controller.request.CommandRequest
import ru.andrey.remote.controller.response.CommandResponse
import ru.andrey.remote.entity.Action
import ru.andrey.remote.entity.Command

fun CommandRequest.toModel(): Command {
    val action = Action.values()
            .find { it.name.equals(action, ignoreCase = true) }
            ?: Action.UNKNOWN

    return Command(deviceId!!, action, payload)
}

fun Command.toDto() = CommandResponse(
        id = id!!,
        deviceId = deviceId,
        action = action.toString(),
        payload = payload,
        completed = completed,
        failed = failed,
        time = time
)
