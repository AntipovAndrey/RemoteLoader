package ru.andrey.remote.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("remoteloader")
class RemoteLoaderConfig {

    lateinit var uploadDir: String
}
