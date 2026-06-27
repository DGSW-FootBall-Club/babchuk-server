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
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다"),
    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용 중인 아이디입니다"),
    STUDENT_ID_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 등록된 학번입니다"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다"),
}
