package com.patronus.devicemanager.web.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {
    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleStudentNotFoundException(exception: NotFoundException): ResponseEntity<Any> {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.message)
    }

    @ExceptionHandler(AlreadyExistsException::class)
    fun handleStudentAlreadyExistsException(exception: AlreadyExistsException): ResponseEntity<Any> {
        return buildErrorResponse(HttpStatus.CONFLICT, exception.message)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<Any> {
        log.error("Unexpected runtime exception.", exception)
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Server error, please check logs.")
    }

    fun buildErrorResponse(status: HttpStatus, message: String?): ResponseEntity<Any> {
        return ResponseEntity
                .status(status)
                .body(ErrorResponse(status.value().toString(), message))
    }
}

