package ru.andrey.domain.repository

import java.io.File

interface DeviceRepository {

    fun getDevicesFiles(): List<File>

    fun getFile(path: String): File
}
