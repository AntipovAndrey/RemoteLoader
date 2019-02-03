package ru.andrey.remote.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.andrey.remote.controller.request.CommandRequest
import ru.andrey.remote.controller.response.CommandResponse
import ru.andrey.remote.entity.Action
import ru.andrey.remote.entity.Command
import ru.andrey.remote.repository.CommandRepository
import ru.andrey.remote.service.exception.CommandCompletedException
import ru.andrey.remote.service.exception.CommandException
import ru.andrey.remote.service.exception.NoSuchCommandException
import ru.andrey.remote.service.exception.UnknownActionException
import ru.andrey.remote.service.mapping.toDto
import ru.andrey.remote.service.mapping.toModel

@Service
class CommandService(
        private val commandRepository: CommandRepository,
        private val deviceService: DeviceService
) {

    private val log = LoggerFactory.getLogger(CommandService::class.java)

    fun saveCommand(deviceId: String, requestList: List<CommandRequest>) {
        deviceService.assertDevicePresent(deviceId)
        val verifiedCommands = requestList.map { request ->
            val command = request.toModel(deviceId)
            if (command.action == Action.UNKNOWN) {
                log.info("unknown command action {} ", request.action)
                throw UnknownActionException(request.action)
            }
            command
        }.toList()

        val saved = commandRepository.saveAll(verifiedCommands)
        log.info("commands {} saved", saved)
    }

    fun fetchCommands(deviceId: String): List<CommandResponse> {
        deviceService.assertDevicePresent(deviceId)
        return commandRepository
                .findByDeviceId(deviceId)
                .map { it.toDto() }
    }

    fun fetchPendingCommands(deviceId: String): List<CommandResponse> {
        deviceService.assertDevicePresent(deviceId)
        return commandRepository
                .findByDeviceIdAndCompletedAndFailed(deviceId, completed = false, failed = false)
                .map { it.toDto() }
    }

    fun failCommand(deviceId: String, commandId: String) {
        deviceService.assertDevicePresent(deviceId)
        val command = findCommandOrThrow(commandId)
        assertCommandNotCompleted(command)
        command.failed = true
        val saved = commandRepository.save(command)
        log.info("command failed {}", saved)
    }

    fun assertCommandOfAction(commandId: String, action: Action) {
        val command = findCommandOrThrow(commandId)
        assertCommandNotCompleted(command)
        if (command.action !== action) {
            throw CommandException("Can't complete command with wrong action. " +
                    "Expected ${command.action} got $action")
        }
    }

    fun completeCommand(deviceId: String, commandId: String) {
        deviceService.assertDevicePresent(deviceId)
        val command = findCommandOrThrow(commandId)
        command.completed = true
        val saved = commandRepository.save(command)
        log.info("command completed {}", saved)
    }

    fun assertCommandPresent(id: String) {
        if (!commandRepository.existsById(id)) {
            log.info("unknown command {} {}", id)
            throw NoSuchCommandException(id)
        }
    }

    private fun assertCommandNotCompleted(command: Command) {
        if (command.completed) {
            log.info("command already completed {}", command.id)
            throw CommandCompletedException(command.id.toString())
        }
    }

    private fun findCommandOrThrow(id: String): Command {
        assertCommandPresent(id)
        return commandRepository.findById(id).get()
    }
}