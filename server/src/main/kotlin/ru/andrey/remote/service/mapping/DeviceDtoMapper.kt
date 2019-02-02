package ru.andrey.remote.service.mapping

import ru.andrey.remote.controller.request.DeviceRequest
import ru.andrey.remote.controller.response.DeviceResponse
import ru.andrey.remote.entity.Device

fun DeviceRequest.toModel() = Device(deviceId!!, name!!)

fun Device.toDto() = DeviceResponse(deviceId, name)
