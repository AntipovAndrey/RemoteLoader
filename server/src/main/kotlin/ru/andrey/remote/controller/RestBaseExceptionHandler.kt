package ru.andrey.remote.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.andrey.remote.service.exception.BaseException

@ControllerAdvice
class RestBaseExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BaseException::class)
    fun handleException(exception: BaseException, request: WebRequest): ResponseEntity<Any> {
        data class Error(val message: String?, val status: Int)

        val status = HttpStatus.BAD_REQUEST
        return handleExceptionInternal(exception,
                Error(exception.message, status.value()),
                HttpHeaders(),
                status,
                request)
    }
}