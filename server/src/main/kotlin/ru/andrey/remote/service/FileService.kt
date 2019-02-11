package ru.andrey.remote.service

import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import ru.andrey.remote.config.RemoteLoaderConfig
import ru.andrey.remote.entity.Action
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileService(
        config: RemoteLoaderConfig,
        private val deviceService: DeviceService,
        private val commandService: CommandService
) {

    private val fileStorageLocation: Path = Paths.get(config.uploadDir)
            .toAbsolutePath()
            .normalize()
            .also { Files.createDirectories(it) }

    fun saveFile(file: MultipartFile, deviceId: String, commandId: String) {
        deviceService.assertDevicePresent(deviceId)
        commandService.assertCommandOfAction(commandId, Action.FETCH_FILE)
        saveFile(file)
    }

    private fun saveFile(file: MultipartFile) : String {
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        val targetLocation = fileStorageLocation.resolve(fileName)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
        return fileName
    }

}
