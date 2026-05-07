package com.rice.babchuk.global.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse.of(e.error)
        return ResponseEntity.status(e.status).body(body)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadable(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = e.mostSpecificCause.message ?: "요청 본문을 파싱할 수 없습니다",
        )
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val message = e.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
            .ifBlank { "요청 값이 유효하지 않습니다" }
        val body = ErrorResponse(status = HttpStatus.BAD_REQUEST.value(), message = message)
        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        e.printStackTrace()

        val body = ErrorResponse.of(GlobalError.INTERNAL_SERVER_ERROR)
        return ResponseEntity.status(body.status).body(body)
    }
}
