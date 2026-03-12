package com.rice.babchuk.domain.match.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

@Schema(description = "매치 생성 요청")
data class MatchRequest(
    val title: String,

    @Schema(example = "2025-03-10")
    val matchDate: LocalDate,
    @Schema(example = "20:00")
    val matchTime: LocalTime,

    @Schema(example = "14")
    val teamSize: Int,

    @Schema(example = "30")
    val durationMinutes: Int,

    @Schema(example = "1")
    val teamACaptainId: Long,

    @Schema(example = "2")
    val teamBCaptainId: Long
)