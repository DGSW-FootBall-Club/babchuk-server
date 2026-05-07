package com.rice.babchuk.domain.auth.error

import com.rice.babchuk.global.error.CustomError
import org.springframework.http.HttpStatus

enum class AuthError(
    override val status: HttpStatus,
    override val message: String
) : CustomError {
    INVALID_DAUTH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 DAuth Access Token입니다"),
    DAUTH_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "DAuth 서버와 통신할 수 없습니다"),
    NOT_STUDENT_ACCOUNT(HttpStatus.FORBIDDEN, "학생 계정만 로그인할 수 있습니다"),
}
