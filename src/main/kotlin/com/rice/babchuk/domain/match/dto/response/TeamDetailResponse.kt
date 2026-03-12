package com.rice.babchuk.domain.match.dto.response

import com.rice.babchuk.domain.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema

data class TeamDetailResponse(
    val captain: UserResponse,
    val members: List<UserResponse>,
    @Schema(example = "0")
    val currentSize: Int,
)