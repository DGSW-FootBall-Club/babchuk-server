package com.rice.babchuk.domain.match.dto.request
import com.rice.babchuk.domain.match.domain.enum.TeamType
import jakarta.validation.constraints.NotNull

data class JoinMatchRequest(
    @field:NotNull(message = "팀을 선택해주세요")
    val teamType: TeamType
)