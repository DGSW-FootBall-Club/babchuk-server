package com.rice.babchuk.domain.match.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "매치 생성 요청")
data class CreateMatchRequest(
    val title: String,
    val matchAt: LocalDateTime,
    @Schema(example = "14")
    val teamSize: Int,
    @Schema(example = "30")
    val durationMinutes: Int,
    @Schema(example = "1")
    val teamACaptainId: Long,
    @Schema(example = "2")
    val teamBCaptainId: Long
)