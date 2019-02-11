package ru.andrey.remote.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.andrey.remote.entity.UploadedFiles

interface UploadedFileRepository : MongoRepository<UploadedFiles, String> {

    fun findByDeviceId(deviceId: String): List<UploadedFiles>
}
