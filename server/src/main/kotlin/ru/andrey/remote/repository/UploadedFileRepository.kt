package ru.andrey.remote.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.andrey.remote.entity.UploadedFile

interface UploadedFileRepository : MongoRepository<UploadedFile, String> {

    fun findByDeviceId(deviceId: String): List<UploadedFile>
}
