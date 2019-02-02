package ru.andrey.remote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RemoteApplication

fun main(args: Array<String>) {
    runApplication<RemoteApplication>(*args)
}

