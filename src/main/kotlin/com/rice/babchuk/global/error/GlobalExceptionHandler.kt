package com.rice.babchuk.global.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * CustomException 처리
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse.of(e.error)
        return ResponseEntity.status(e.status).body(body)
    }

    /**
     * 모든 Exception 처리 (Fallback)
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        e.printStackTrace() // 서버 콘솔에 로그

        val body = ErrorResponse.of(GlobalError.INTERNAL_SERVER_ERROR)
        return ResponseEntity.status(body.status).body(body)
    }
}