package ru.andrey.remote.controller.request

import javax.validation.constraints.NotEmpty

class CommandRequest(

        @field:NotEmpty
        val action: String?,

        val payload: String?
)
