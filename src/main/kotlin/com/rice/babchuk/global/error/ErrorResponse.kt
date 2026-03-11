package com.rice.babchuk.global.error

data class ErrorResponse(
    val status: Int,
    val message: String
) {
    companion object {
        fun of(customError: CustomError): ErrorResponse {
            return ErrorResponse(
                status = customError.status.value(), message = customError.message
            )
        }
    }
}