package ru.andrey.remote.service.mapping

import ru.andrey.remote.controller.request.DeviceFilesInfoRequest
import ru.andrey.remote.controller.response.DeviceFilesInfoResponse
import ru.andrey.remote.controller.response.FileInfoResponse
import ru.andrey.remote.entity.DeviceFilesInfo
import ru.andrey.remote.entity.FileInfo

fun DeviceFilesInfoRequest.toModel() = DeviceFilesInfo(
        deviceId = deviceId!!,
        commandId = commandId!!,
        filesInfo = filesInfo.map { FileInfo(it.path, it.size) }
)

fun DeviceFilesInfo.toDto() = DeviceFilesInfoResponse(
        deviceId = deviceId,
        commandId = commandId,
        filesInfo = filesInfo.map { FileInfoResponse(it.path, it.size) },
        time = time
)
