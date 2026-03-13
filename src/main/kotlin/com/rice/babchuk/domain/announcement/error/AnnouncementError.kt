package com.rice.babchuk.domain.announcement.error

import com.rice.babchuk.global.error.CustomError
import org.springframework.http.HttpStatus

enum class AnnouncementError(
    override val status: HttpStatus,
    override val message: String
) : CustomError {
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다"),
}