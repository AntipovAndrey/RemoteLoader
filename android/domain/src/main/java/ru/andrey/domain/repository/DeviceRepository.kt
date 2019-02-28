package ru.andrey.domain.repository

import java.io.File

interface DeviceRepository {

    /**
     *  Returns all files starting from the root
     */
    fun getDevicesFiles(): List<File>

    /**
     *  Returns one file by a given path
     */
    fun getFile(path: String): File
}
