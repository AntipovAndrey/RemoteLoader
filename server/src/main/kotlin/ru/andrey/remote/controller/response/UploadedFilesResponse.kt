package ru.andrey.remote.controller.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UploadedFilesResponse(
        val deviceId: String,
        val filesInfo: List<UploadedFileInfo>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UploadedFileInfo(val commandId: String, val path: String)
