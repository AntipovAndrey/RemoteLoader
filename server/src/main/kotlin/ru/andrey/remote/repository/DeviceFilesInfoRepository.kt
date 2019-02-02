package ru.andrey.remote.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.andrey.remote.entity.DeviceFilesInfo

interface DeviceFilesInfoRepository : MongoRepository<DeviceFilesInfo, String> {

    fun findByDeviceIdAndExpired(deviceId: String, expired: Boolean): List<DeviceFilesInfo>
}
