package ru.andrey.remote.controller.request

import javax.validation.constraints.NotEmpty

data class DeviceRequest(

        @field:NotEmpty
        val deviceId: String?,

        @field:NotEmpty
        val name: String?
)
