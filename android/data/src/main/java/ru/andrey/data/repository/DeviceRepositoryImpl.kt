package ru.andrey.data.repository

import ru.andrey.domain.repository.DeviceRepository
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class DeviceRepositoryImpl : DeviceRepository {

    override fun getDevicesFiles(): List<File> {
        return getDevicesFiles(File("/"))
    }

    private fun getDevicesFiles(parentDir: File): List<File> {
        val filesResult = ArrayList<File>()
        val filesAndDirectoriesQueue = LinkedList<File>()

        filesAndDirectoriesQueue.addAll(parentDir.listFiles())

        while (!filesAndDirectoriesQueue.isEmpty()) {
            val file = filesAndDirectoriesQueue.remove()
            if (file.isDirectory) {
                filesAndDirectoriesQueue.addAll(file.listFiles())
            } else {
                filesResult.add(file)
            }
        }

        return filesResult
    }
}