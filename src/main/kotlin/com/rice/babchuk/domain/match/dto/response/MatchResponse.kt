package com.rice.babchuk.domain.match.dto.response

import com.rice.babchuk.domain.match.domain.enum.MatchStatus
import com.rice.babchuk.domain.user.dto.response.UserResponse
import java.time.LocalDate
import java.time.LocalTime

data class MatchResponse(
    val id: Long,
    val title: String,
    val matchDate: LocalDate,
    val matchTime: LocalTime,
    val teamSize: Int,
    val durationMinutes: Int,
    val teamACaptain: UserResponse,
    val teamBCaptain: UserResponse,
    val status: MatchStatus,
)