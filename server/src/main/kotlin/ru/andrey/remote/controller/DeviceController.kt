package ru.andrey.remote.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.andrey.remote.controller.request.DeviceRequest
import ru.andrey.remote.service.DeviceService
import javax.validation.Valid

@RestController
@RequestMapping("devices")
class DeviceController(
        private val deviceService: DeviceService
) {

    @PostMapping("save")
    fun saveDevice(@Valid @RequestBody request: DeviceRequest): ResponseEntity<*> {
        val saved = deviceService.saveDevice(request)
        val code = if (saved) HttpStatus.CREATED else HttpStatus.OK
        return ResponseEntity<Nothing>(code)
    }

    @GetMapping("all")
    fun getAll() = deviceService.getAll()
}