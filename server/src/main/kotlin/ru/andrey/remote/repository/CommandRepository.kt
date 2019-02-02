package ru.andrey.remote.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.andrey.remote.entity.Command

interface CommandRepository : MongoRepository<Command, String> {

    fun findByDeviceId(deviceId: String): List<Command>

    fun findByDeviceIdAndCompletedAndFailed(deviceId: String, completed: Boolean, failed: Boolean): List<Command>
}
