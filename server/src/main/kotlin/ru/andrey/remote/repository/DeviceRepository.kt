package ru.andrey.remote.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.andrey.remote.entity.Device

interface DeviceRepository : MongoRepository<Device, String> {

    fun findByDeviceId(deviceId: String): Device

    fun existsByDeviceId(deviceId: String): Boolean
}
