package ru.andrey.remote.controller

import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.andrey.remote.controller.response.UploadedFiles
import ru.andrey.remote.service.FileService

@RestController
@RequestMapping("files")
class FilesController(
        private val service: FileService
) {

    @PostMapping("upload")
    fun uploadFiles(
            @RequestParam("device") deviceId: String,
            @RequestParam("command") commandId: String,
            @RequestParam("files") files: List<MultipartFile>
    ) {
        files.forEach { file -> service.saveFile(file, deviceId, commandId) }
    }

    @GetMapping("all/{deviceId}")
    fun getStoredFilesForDevice(@PathVariable deviceId: String): List<UploadedFiles> {
        return emptyList()
    }

    @GetMapping("load/{deviceId}/{location}")
    fun loadFile(@PathVariable deviceId: String, @PathVariable location: String): ResponseEntity<Resource> {
        val resource = service.loadFile(deviceId, location)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${resource.filename}\"")
                .body(resource)
    }
}
