package com.rice.babchuk.global.common.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.ResponseEntity

data class BaseResponse<T>(
    @Schema(description = "HTTP 상태 코드", example = "200")
    val status: Int,
    val message: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T? = null,
) {
    companion object {
        fun <T> of(data: T, status: Int = 200, message: String = ""): ResponseEntity<BaseResponse<T>> {
            return ResponseEntity.status(status)
                .body(BaseResponse(status = status, message = message, data = data))
        }

        fun success(message: String = "", status: Int = 200): ResponseEntity<BaseResponse<Nothing>> {
            return ResponseEntity.status(status)
                .body(BaseResponse(status = status, message = message, data = null))
        }
    }
}