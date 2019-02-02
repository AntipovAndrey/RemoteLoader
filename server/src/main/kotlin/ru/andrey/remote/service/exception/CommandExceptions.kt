package ru.andrey.remote.service.exception

open class CommandException(message: String) : BaseException(message)

class NoSuchCommandException(id: String) : CommandException("Unknown command: $id")

class CommandCompletedException(id: String)
    : CommandException("Command already completed $id")
