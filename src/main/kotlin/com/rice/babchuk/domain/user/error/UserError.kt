package com.rice.babchuk.domain.user.error

import com.rice.babchuk.global.error.CustomError
import org.springframework.http.HttpStatus

enum class UserError(
    override val status: HttpStatus,
    override val message: String
) : CustomError {
    USER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. (ID: %s)"),
}
