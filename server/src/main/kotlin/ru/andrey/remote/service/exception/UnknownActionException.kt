package ru.andrey.remote.service.exception

import ru.andrey.remote.entity.Action

class UnknownActionException(action: String?)
    : BaseException("Unknown Action: $action. Supported actions: ${Action.values().filter { it != Action.UNKNOWN }}")
