package com.rice.babchuk.domain.match.error

import com.rice.babchuk.global.error.CustomError
import org.springframework.http.HttpStatus

enum class MatchError(override val status: HttpStatus, override val message: String) : CustomError {
    CAPTAIN_NOT_FOUND(HttpStatus.NOT_FOUND, "주장으로 선택한 유저를 찾을 수 없습니다"),
    SAME_CAPTAIN(HttpStatus.BAD_REQUEST, "A팀과 B팀 주장은 다른 유저여야 합니다"),
    MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "매치를 찾을 수 없습니다"),
    ALREADY_JOINED(HttpStatus.BAD_REQUEST, "이미 신청한 매치입니다"),
    TEAM_FULL(HttpStatus.BAD_REQUEST, "팀 인원이 꽉 찼습니다"),
    CAPTAIN_CANNOT_JOIN(HttpStatus.BAD_REQUEST, "주장은 팀 신청을 할 수 없습니다"),
    NOT_JOINED(HttpStatus.BAD_REQUEST, "신청하지 않은 매치입니다"),
    CANNOT_MODIFY(HttpStatus.BAD_REQUEST, "종료되거나 마감된 매치는 수정할 수 없습니다"),
    NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "권한이 없습니다"),
    MATCH_NOT_OPEN(HttpStatus.BAD_REQUEST, "모집이 종료된 매치는 신청할 수 없습니다"),
}