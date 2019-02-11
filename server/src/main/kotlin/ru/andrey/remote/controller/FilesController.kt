package ru.andrey.remote.controller

import org.springframework.core.io.Resource
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

    }

    @GetMapping("all/{deviceId}")
    fun getStoredFilesForDevice(@PathVariable deviceId: String): List<UploadedFiles> {
        return emptyList()
    }

    @GetMapping("load/{fileId}")
    fun loadFile(@PathVariable fileId: String): Resource {
        throw NotImplementedError()
    }
}
