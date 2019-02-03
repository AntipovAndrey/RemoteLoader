package ru.andrey.remote.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.andrey.remote.controller.request.DeviceFilesInfoRequest
import ru.andrey.remote.controller.response.DeviceFilesInfoResponse
import ru.andrey.remote.entity.Action
import ru.andrey.remote.repository.DeviceFilesInfoRepository
import ru.andrey.remote.service.mapping.toDto
import ru.andrey.remote.service.mapping.toModel

@Service
class DeviceFilesInfoService(
        private val deviceFilesInfoRepository: DeviceFilesInfoRepository,
        private val commandService: CommandService,
        private val deviceService: DeviceService
) {

    private val log = LoggerFactory.getLogger(CommandService::class.java)

    fun saveDeviceFilesInfo(request: DeviceFilesInfoRequest) {
        val filesInfo = request.toModel()
        deviceService.assertDevicePresent(filesInfo.deviceId)
        commandService.assertCommandOfAction(filesInfo.commandId, Action.QUERY_LIST)

        deviceFilesInfoRepository
                .findByDeviceIdAndExpired(filesInfo.deviceId, expired = false)
                .filterNot { it.expired }
                .onEach { it.expired = true }
                .let { deviceFilesInfoRepository.saveAll(it) }

        deviceFilesInfoRepository.save(filesInfo)
        commandService.completeCommand(filesInfo.deviceId, filesInfo.commandId)
        log.info("device files info saved for {}", filesInfo.deviceId)
    }

    fun fetchResentFilesInfo(deviceId: String): List<DeviceFilesInfoResponse> {
        deviceService.assertDevicePresent(deviceId)
        return deviceFilesInfoRepository
                .findByDeviceIdAndExpired(deviceId, false)
                .map { it.toDto() }
    }
}
