package ru.andrey.remote.controller

import org.springframework.web.bind.annotation.*
import ru.andrey.remote.controller.request.DeviceFilesInfoRequest
import ru.andrey.remote.service.DeviceFilesInfoService
import javax.validation.Valid

@RestController
@RequestMapping("paths")
class DeviceFilesInfoController(
        private val deviceFilesInfoService: DeviceFilesInfoService
) {

    @PostMapping("save")
    fun saveFilesInfo(@Valid @RequestBody request: DeviceFilesInfoRequest)= deviceFilesInfoService.saveDeviceFilesInfo(request)

    @GetMapping("recent/{deviceId}")
    fun getMostRecentInfo(@PathVariable deviceId: String) = deviceFilesInfoService.fetchResentFilesInfo(deviceId)
}
