package ru.andrey.remote.service

import org.springframework.stereotype.Service
import ru.andrey.remote.controller.request.DeviceRequest
import ru.andrey.remote.repository.DeviceRepository
import ru.andrey.remote.service.mapping.toDto
import ru.andrey.remote.service.mapping.toModel

@Service
class DeviceService(
        private val deviceRepository: DeviceRepository
) {

    /**
     * Saves device in the database if absent
     *
     * @param request device to be saved
     * @return true if saved, false if device is present
     */
    fun saveDevice(request: DeviceRequest): Boolean {
        val device = request.toModel()
        if (deviceRepository.existsByDeviceId(device.deviceId)) {
            return false
        }
        deviceRepository.save(device)
        return true
    }

    fun getAll() = deviceRepository.findAll().map { it.toDto() }
}
