package ru.andrey.remote.service

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import ru.andrey.remote.config.RemoteLoaderConfig
import ru.andrey.remote.entity.Action
import ru.andrey.remote.entity.UploadedFile
import ru.andrey.remote.repository.UploadedFileRepository
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

typealias UploadedResponse = ru.andrey.remote.controller.response.UploadedFile

@Service
class FileService(
        config: RemoteLoaderConfig,
        private val deviceService: DeviceService,
        private val commandService: CommandService,
        private val fileRepository: UploadedFileRepository
) {

    private val log = LoggerFactory.getLogger(FileService::class.java)

    private val fileStorageLocation: Path = Paths.get(config.uploadDir)
            .toAbsolutePath()
            .normalize()
            .also { Files.createDirectories(it) }

    fun saveFile(file: MultipartFile, path: String, deviceId: String, commandId: String) {
        deviceService.assertDevicePresent(deviceId)
        commandService.assertCommandOfAction(commandId, Action.FETCH_FILE)
        log.info("saving {} for {}", file.originalFilename, deviceId)
        try {
            val location = writeFile(file, path, deviceId)
            fileRepository.save(UploadedFile(deviceId, commandId, path, location))
            commandService.completeCommand(deviceId, commandId)
        } catch (e: Exception) {
            log.warn("error during saving file", e)
            commandService.failCommand(deviceId, commandId)
        }
    }

    fun loadFile(location: String, deviceId: String): Resource {
        val path = fileStorageLocation.resolve(deviceId).resolve(location).normalize()
        return UrlResource(path.toUri())
    }

    private fun writeFile(file: MultipartFile, path: String, deviceId: String): String {
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        val targetDirectory = fileStorageLocation.resolve(deviceId)
        targetDirectory.takeIf { Files.notExists(it) }?.let { Files.createDirectory(it) }
        val targetLocation = targetDirectory.resolve(fileName)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
        return fileName
    }

    fun getStored(deviceId: String): List<UploadedResponse> {
        return fileRepository.findByDeviceId(deviceId)
                .map { UploadedResponse(it.deviceId, it.commandId, it.path, it.file) }
    }
}
