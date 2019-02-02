package ru.andrey.remote.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.andrey.remote.controller.request.CommandRequest
import ru.andrey.remote.service.CommandService
import javax.validation.Valid

@RestController
@RequestMapping("commands")
class CommandController(
        private val commandService: CommandService
) {

    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveCommand(@Valid @RequestBody request: List<CommandRequest>) = commandService.saveCommand(request)

    @PostMapping("fail")
    fun failCommand(
            @RequestParam("device") deviceId: String,
            @RequestParam("command") commandId: String
    ) = commandService.failCommand(deviceId, commandId)

    @GetMapping("all/{deviceId}")
    fun getByDevice(@PathVariable deviceId: String) = commandService.fetchCommands(deviceId)

    @GetMapping("pending/{deviceId}")
    fun getPendingByDevice(@PathVariable deviceId: String) = commandService.fetchPendingCommands(deviceId)
}
