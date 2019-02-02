package ru.andrey.remote.service

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.andrey.remote.controller.request.CommandRequest
import ru.andrey.remote.controller.response.CommandResponse
import ru.andrey.remote.entity.Action
import ru.andrey.remote.entity.Command
import ru.andrey.remote.repository.CommandRepository
import ru.andrey.remote.repository.DeviceRepository
import ru.andrey.remote.service.exception.CommandCompletedException
import ru.andrey.remote.service.exception.NoSuchCommandException
import ru.andrey.remote.service.exception.NoSuchDeviceException
import ru.andrey.remote.service.exception.UnknownActionException
import ru.andrey.remote.service.mapping.toDto
import ru.andrey.remote.service.mapping.toModel

@Service
class CommandService(
        private val commandRepository: CommandRepository,
        private val deviceRepository: DeviceRepository
) {

    private val log = LoggerFactory.getLogger(CommandService::class.java)

    fun saveCommand(requestList: List<CommandRequest>) {
        val verifiedCommands = requestList.map { request ->
            val command = request.toModel()
            assertDevicePresents(command.deviceId)
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
        assertDevicePresents(deviceId)
        return commandRepository
                .findByDeviceId(deviceId)
                .map { it.toDto() }
    }

    fun fetchPendingCommands(deviceId: String): List<CommandResponse> {
        assertDevicePresents(deviceId)
        return commandRepository
                .findByDeviceIdAndCompletedAndFailed(deviceId, completed = false, failed = false)
                .map { it.toDto() }
    }

    fun failCommand(deviceId: String, commandId: String) {
        assertDevicePresents(deviceId)
        val command = findCommandOrThrow(commandId)
        if (command.completed) {
            log.info("command already completed {} {}", commandId, deviceId)
            throw CommandCompletedException(commandId)
        }
        command.failed = true
        val saved = commandRepository.save(command)
        log.info("command failed {}", saved)
    }

    fun completeCommand(deviceId: String, commandId: String) {
        assertDevicePresents(deviceId)
        val command = findCommandOrThrow(commandId)
        command.completed = true
        val saved = commandRepository.save(command)
        log.info("command completed {}", saved)
    }

    private fun findCommandOrThrow(id: String): Command {
        return commandRepository.findByIdOrNull(id) ?: run {
            log.info("unknown command {} {}", id)
            throw NoSuchCommandException(id)
        }
    }

    private fun assertDevicePresents(deviceId: String) {
        if (!deviceRepository.existsByDeviceId(deviceId)) {
            log.info("unknown device {} ", deviceId)
            throw NoSuchDeviceException(deviceId)
        }
    }
}