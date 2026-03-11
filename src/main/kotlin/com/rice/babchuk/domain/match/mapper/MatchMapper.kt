package com.rice.babchuk.domain.match.mapper

import com.rice.babchuk.domain.match.domain.entity.Match
import com.rice.babchuk.domain.match.dto.request.CreateMatchRequest
import com.rice.babchuk.domain.user.domain.entity.User
import org.springframework.stereotype.Component

@Component
object MatchMapper {
    fun toEntity(
        request: CreateMatchRequest,
        teamACaptain: User,
        teamBCaptain: User
    ): Match {
        return Match(
            title = request.title,
            matchAt = request.matchAt,
            teamSize = request.teamSize,
            durationMinutes = request.durationMinutes,
            teamACaptain = teamACaptain,
            teamBCaptain = teamBCaptain
        )
    }
}