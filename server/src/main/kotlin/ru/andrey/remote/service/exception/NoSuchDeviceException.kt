package ru.andrey.remote.service.exception

class NoSuchDeviceException(deviceId: String) : BaseException("Unknown device: $deviceId")
