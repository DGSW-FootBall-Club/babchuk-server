package com.rice.babchuk.domain.auth.error

import com.rice.babchuk.global.error.CustomError
import org.springframework.http.HttpStatus

enum class AuthError(
    override val status: HttpStatus,
    override val message: String
) : CustomError {
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다"),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "사용자를 찾을 수 없습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다"),
    GRADE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용 중인 학번이에요")
}

