package com.rice.babchuk.domain.match.dto.response

import com.rice.babchuk.domain.match.domain.enum.MatchStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

data class MatchDetailResponse(
    @Schema(example = "0")
    val id: Long ,

    val title: String,

    val matchDate: LocalDate,

    val matchTime: LocalTime,

    @Schema(example = "14")
    val teamSize: Int,

    @Schema(example = "30")
    val durationMinutes: Int,

    val status: MatchStatus,

    val teamA: TeamDetailResponse,

    val teamB: TeamDetailResponse,
)